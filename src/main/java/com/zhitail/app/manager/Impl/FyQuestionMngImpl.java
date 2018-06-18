package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Application;
import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.dao.FyQuestionDao;
import com.zhitail.app.dao.FySensitiveDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyQuestion.Status;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.entity.middle.QuestionUser;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyQuestionMngImpl implements FyQuestionMng {
	@Autowired
	private FyQuestionDao questionDao;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyUserMng userMng;

	public void delete(Long[] ids, FyUser user) {
		// TODO Auto-generated method stub

		for (Long id : ids) {
			List<FyTest> ft = testMng.findByQuestionId(id);
			QuestionConfig qc = new QuestionConfig(id, null);
			for (FyTest f : ft) {

				List<QuestionConfig> qids = f.getQuestionConfigs();
				qids.remove(qc);

				f.setJson(JSONArray.toJSONString(qids));
				f.refreshCode();
				testMng.update(f);

			}
			questionDao.delete(id);
		}

		user.setQuestionCount(getCount(user.getId(),null));
		user.setRecycleCount(getCount(user.getId(),true));
		userMng.update(user);
	}
	@Override
	public Long getCount(Long userId,Boolean isRecycle) {

		Finder finder = Finder.create(" from FyQuestion bean where bean.userId=:userId");
		finder.setParam("userId", userId);
		if(isRecycle!=null) {
		finder.append(" and bean.isRecycle=:isRecycle");
		finder.setParam("isRecycle", isRecycle);
		}
		Integer x = questionDao.countQueryResult(finder);

		return x.longValue();

	}

	public FyQuestion update(FyQuestion question,Boolean isChange) {
		
		// TODO Auto-generated method stub
		Updater u = new Updater(question);
		question = questionDao.updateByUpdater(u);
		if(isChange!=null&&isChange) {
		List<FyTest> list = 	testMng.findByQuestionId(question.getId());
		for(FyTest t:list) {
			t.setCode(Application.getSnowflakeIdWorker().nextId()+"");
			testMng.update(t);
		}
			
		}
		
		return question;
	}

	public FyQuestion save(FyQuestion question, FyUser user) {
		// TODO Auto-generated method stub

		question = questionDao.save(question);
		user.setQuestionCount(getCount(user.getId(),null));
		userMng.update(user);
		return question;
	}

	@Override
	public FyQuestion findById(Long id) {
		// TODO Auto-generated method stub
		return questionDao.findOne(id);
	}

	@Override
	public Pagination<FyQuestion> getPage(String sorter, Long[] alreadyIds, Integer pageNo, Integer pageSize,
			Long userId, String title, String type, String difficulty, String status, String tag) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyQuestion bean where 1=1");

		if (StringUtils.isNotBlank(title)) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(tag)) {
			finder.append(" and bean.tagsJson like:tag");
			finder.setParam("tag", "%\"" + tag + "\"%");
		}
		if (StringUtils.isNotBlank(type)) {
			String[] t = type.split(",");
			List<Type> tt = new ArrayList<Type>();
			for (String s : t) {
				tt.add(Type.valueOf(s));
			}
			finder.append(" and bean.type in:types");
			finder.setParamList("types", tt.toArray(new Type[tt.size()]));
		}
		if (StringUtils.isNotBlank(status)) {
			String[] t = status.split(",");
			List<Status> tt = new ArrayList<Status>();
			for (String s : t) {
				tt.add(Status.valueOf(s));
			}
			finder.append(" and bean.status in:statuss");
			finder.setParamList("statuss", tt.toArray(new Status[tt.size()]));
		}
		if (StringUtils.isNotBlank(difficulty)) {
			String[] t = difficulty.split(",");
			List<Integer> tt = new ArrayList<Integer>();
			for (String s : t) {
				tt.add(Integer.parseInt(s));
			}
			finder.append(" and bean.difficulty in:difficultys");
			finder.setParamList("difficultys", tt.toArray(new Integer[tt.size()]));
		}
		if (alreadyIds != null) {
			finder.append(" and bean.id NOT in:alreadyIds");
			finder.setParamList("alreadyIds", alreadyIds);
		}

		finder.append(" and bean.userId=:userId");
		finder.setParam("userId", userId);
		finder.append(" and bean.isRecycle=false");
		if (StringUtils.isNotBlank(sorter) && sorter.equals("createTime_ascend")) {
			finder.append(" order by bean.createTime asc");
		} else {
			finder.append(" order by bean.createTime desc");
		}

		return questionDao.findPageByFinder(finder, pageNo, pageSize);
	}

	@Override
	public List<FyQuestion> findByIds(Long[] qids) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyQuestion bean where bean.id in:qids and bean.isRecycle=false");

		finder.setParamList("qids", qids);

		return questionDao.findListByFinder(finder);
	}

	@Override
	public void recycle(Long[] ids,FyUser user) {
		// TODO Auto-generated method stub
		FyQuestion q;
		for (Long id : ids) {

			q = this.findById(id);
			if (q != null) {
				q.setRecycleTime(new Date());
				q.setIsRecycle(true);
				this.update(q,null);
			}
		}
		user.setRecycleCount(getCount(user.getId(),true));
		userMng.update(user);
	}

	@Override
	public Pagination<FyQuestion> getRecyclePage(String sorter, Integer pageNo, Integer pageSize, Long userId,
			String title, String tag) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyQuestion bean where 1=1");

		if (StringUtils.isNotBlank(title)) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(tag)) {
			finder.append(" and bean.tagsJson like:tag");
			finder.setParam("tag", "%\"" + tag + "\"%");
		}

		finder.append(" and bean.userId=:userId");
		finder.setParam("userId", userId);
		finder.append(" and bean.isRecycle=true");
		if (StringUtils.isNotBlank(sorter) && sorter.equals("recycleTime_ascend")) {
			finder.append(" order by bean.recycleTime asc");
		} else {
			finder.append(" order by bean.recycleTime desc");
		}

		return questionDao.findPageByFinder(finder, pageNo, pageSize);
	}

	@Override
	public void recovery(Long[] ids,FyUser user) {
		// TODO Auto-generated method stub
		FyQuestion q;
		for (Long id : ids) {
			q = this.findById(id);
			if (q != null) {
				q.setRecycleTime(null);
				q.setIsRecycle(false);
				
				this.update(q,null);
			}
		}
		user.setRecycleCount(getCount(user.getId(),true));
		userMng.update(user);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(
				"select new com.zhitail.app.entity.middle.QuestionUser(bean.id,bean.userId) from FyQuestion bean where 1=1");

		finder.append(" and bean.recycleTime <:overTime");
		Date now = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 15);
		finder.setParam("overTime", now);
		finder.append(" and bean.isRecycle=true");
		List<Object> list = questionDao.findObjectListByFinder(finder);

		this.delete(list);
	}

	private void delete(List<Object> x) {
		// TODO Auto-generated method stub
		Long uid = 0L;
		List<Long> list = new ArrayList<Long>();
		QuestionUser qu;
		for (int i = 0;i< x.size();i++) {
			qu = (QuestionUser) x.get(i);

			if (qu.getUid() != uid) {
				if (uid == 0L) {
					uid = qu.getUid();
				} else {
					FyUser user = userMng.findById(uid);
					list.add(qu.getQid());
					this.delete(list.toArray(new Long[list.size()]), user);
					uid = qu.getUid();
					list.clear();
					continue;
				}
			}

			list.add(qu.getQid());
			if(i==x.size()-1) {
				FyUser user = userMng.findById(uid);
				this.delete(list.toArray(new Long[list.size()]), user);
			}
		}
	}

}
