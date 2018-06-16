package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.dao.FyQuestionDao;
import com.zhitail.app.dao.FyTestDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTest.Mode;
import com.zhitail.app.entity.FyTest.Status;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestMngImpl implements FyTestMng {
	@Autowired
	private FyTestDao testDao;
	@Autowired
	private FyQuestionMng questionMng;
	
	public FyTest update(FyTest test) {
		// TODO Auto-generated method stub
		Updater u = new Updater(test);
		return testDao.updateByUpdater(u);
	}

	public FyTest save(FyTest test) {
		// TODO Auto-generated method stub
		return testDao.save(test);
	}

	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			testDao.delete(id);
		}
	}

	@Override
	public FyTest findById(Long id) {
		// TODO Auto-generated method stub
		return testDao.findOne(id);
	}

	@Override
	public FyTest updateTestQuestions(Long id, Long[] qids) {
		FyTest  t= testDao.findOne(id);
		// TODO Auto-generated method stub
		
		List<QuestionConfig> list = new ArrayList<QuestionConfig>();
		for(Long qid:qids) {
			if(qid!=null) {
		QuestionConfig qc = new QuestionConfig(qid,1.0);
		list.add(qc);
			}
		}
		t.setJson(JSONArray.toJSONString(list));
		t.refreshCode();
		return update(t);
	}

	@Override
	public List<FyTest> findByCodes(String[] codes) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (codes!= null) {
			finder.append(" and bean.code in:codes");
			finder.setParamList("codes", codes );
		}

		finder.append("and bean.isRecycle=false order by bean.id desc");
		return testDao.findListByFinder(finder);
	}
	public void fullQuestions(FyTest test) {
		FyQuestion temp;
		List<QuestionConfig> ids = test.getQuestionConfigs();
		if (ids.size() > 0) {
			Long[] qids = new Long[ids.size()];
			for(int i=0;i<ids.size();i++) {
				qids[i]=ids.get(i).getId();
			}
			List<FyQuestion> qs = questionMng.findByIds(qids);
			Map<Long, FyQuestion> map = new HashMap<Long, FyQuestion>(qs.size());
			for (FyQuestion q : qs) {
				map.put(q.getId(), q);
			}
			List<FyQuestion> fqs = new ArrayList<FyQuestion>();
			for (QuestionConfig config : ids) {
				if (map.containsKey(config.getId())) {
					temp= map.get(config.getId());
					temp.setScore(config.getScore());
					fqs.add(temp);
				}
			}
			test.setQuestions(fqs);

		} else {
			test.setQuestions(new ArrayList<FyQuestion>());
		}

	}
	@Override
	public List<FyTest> getList(Integer start, Integer count, FyTest search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search.getUserId() != null) {
			finder.append(" and bean.userId=:userId");
			finder.setParam("userId",search.getUserId() );
		}
		if (search.getIsSale()!= null) {
			finder.append(" and bean.isSale=:isSale");
			finder.setParam("isSale",search.getIsSale() );
		}
		finder.setFirstResult(start);
		finder.setMaxResults(count);
		
		finder.append("and bean.isRecycle=false order by bean.id desc");
		return testDao.findListByFinder(finder);
	}

	@Override
	public FyTest findByCode(String code) {
		// TODO Auto-generated method stub
		return testDao.findByCode(code);
	}

	@Override
	public List<FyTest> findByQuestionId(Long id) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (id != null) {
			finder.append(" and bean.json like:id");
			finder.setParam("id", "%:" + id + ",%");
		}
		return testDao.findListByFinder(finder);
	}

	@Override
	public Pagination<FyTest> getPage(Long userId, Integer pageNo, Integer pageSize,String code, String title, String isQuestionnaire,
			String mode,String status,String sorter) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (StringUtils.isNotBlank(title)) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(code)) {
			finder.append(" and bean.code like:code");
			finder.setParam("code", "%" + code + "%");
		}
		if (StringUtils.isNotBlank(mode)) {
			String[] t = mode.split(",");
			List<Mode> tt = new ArrayList<Mode>();
			for (String s : t) {
				tt.add(Mode.valueOf(s));
			}
			finder.append(" and bean.mode in:modes");
			finder.setParamList("modes", tt.toArray(new Mode[tt.size()]));
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
		
		if (StringUtils.isNotBlank(isQuestionnaire)) {
			String[] t = isQuestionnaire.split(",");
			List<Boolean> tt = new ArrayList<Boolean>();
			for (String s : t) {
				tt.add(Boolean.valueOf(s));
			}
			finder.append(" and bean.isQuestionnaire in:isQuestionnaires");
			finder.setParamList("isQuestionnaires", tt.toArray(new Boolean[tt.size()]));
		}
		finder.append(" and bean.userId=:userId");
		finder.setParam("userId", userId);
		finder.append(" and bean.isRecycle=false ");
		if(StringUtils.isNotBlank(sorter)&&sorter.equals("createTime_ascend")){
			finder.append(" order by bean.createTime asc");
		}else {
			finder.append(" order by bean.createTime desc");
		}
		return testDao.findPageByFinder(finder, pageNo, pageSize);
	}

	@Override
	public void recycle(Long[] ids) {
		// TODO Auto-generated method stub
		FyTest test;
		for (Long id : ids) {
			test=this.findById(id);
			test.setRecycleTime(new Date());
			test.setIsRecycle(true);
			this.update(test);
		}
	}

	@Override
	public Pagination<FyTest> getRecyclePage(Long userId, Integer pageNo, Integer pageSize,String title,String code,String sorter) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Finder finder = Finder.create(" from FyTest bean where 1=1");
				if (StringUtils.isNotBlank(title)) {
					finder.append(" and bean.title like:title");
					finder.setParam("title", "%" + title + "%");
				}
				if (StringUtils.isNotBlank(code)) {
					finder.append(" and bean.code like:code");
					finder.setParam("code", "%" + code + "%");
				}
				finder.append(" and bean.userId=:userId");
				finder.setParam("userId", userId);
				finder.append(" and bean.isRecycle=true ");
				if(StringUtils.isNotBlank(sorter)&&sorter.equals("recycleTime_ascend")){
					finder.append(" order by bean.recycleTime asc");
				}else {
					finder.append(" order by bean.recycleTime desc");
				}
				
				
				return testDao.findPageByFinder(finder, pageNo, pageSize);
			}
}
