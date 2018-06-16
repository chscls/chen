package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.dao.FyQuestionDao;
import com.zhitail.app.dao.FySensitiveDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyQuestion.Status;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
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

	public void delete(Long[] ids) {
		// TODO Auto-generated method stub

		for (Long id : ids) {
			List<FyTest> ft = testMng.findByQuestionId(id);
			QuestionConfig qc = new QuestionConfig(id,null);
			for (FyTest f : ft) {
				
				List<QuestionConfig> qids = f.getQuestionConfigs();
				qids.remove(qc);
				
				f.setJson(JSONArray.toJSONString(qids));
				f.refreshCode();
				testMng.update(f);

			}
			questionDao.delete(id);
		}

	}

	public FyQuestion update(FyQuestion question) {
		// TODO Auto-generated method stub
		Updater u = new Updater(question);
		return questionDao.updateByUpdater(u);
	}

	public FyQuestion save(FyQuestion question) {
		// TODO Auto-generated method stub
		return questionDao.save(question);
	}

	@Override
	public FyQuestion findById(Long id) {
		// TODO Auto-generated method stub
		return questionDao.findOne(id);
	}

	@Override
	public Pagination<FyQuestion> getPage(Long[] alreadyIds, Integer pageNo, Integer pageSize, Long userId,
			String title, String type, String difficulty, String status) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyQuestion bean where 1=1");

		if (StringUtils.isNotBlank(title)) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + title + "%");
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
		finder.append("and bean.isRecycle=false order by bean.id desc");
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
	public void recycle(Long[] ids) {
		// TODO Auto-generated method stub
		FyQuestion q;
		for (Long id : ids) {
			q=this.findById(id);
			q.setIsRecycle(true);
			this.update(q);
		}
	}

}
