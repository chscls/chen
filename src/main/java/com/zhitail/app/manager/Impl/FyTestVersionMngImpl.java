package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.dao.FyTestDao;
import com.zhitail.app.dao.FyTestVersionDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FyTestRecord.Status;
import com.zhitail.app.entity.middle.FyQuestionItem;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyTestVersionMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestVersionMngImpl implements FyTestVersionMng {
	@Autowired
	private FyTestVersionDao testRecordDao;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyQuestionMng questionMng;

	@Override
	public FyTestVersion findById(Long id) {
		// TODO Auto-generated method stub
		return testRecordDao.findOne(id);
	}

	@Override
	public Pagination<String> getPage(Integer pageNo, Integer pageSize, FyTestVersion search,Long orgId) {
		Finder finder0 = Finder.create(" from FyTestVersion bean where 1=1");

		if (search.getTitle() != null) {
			finder0.append(" and bean.title like:title");
			finder0.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search.getCode() != null) {
			finder0.append(" and bean.code =:code");
			finder0.setParam("code", search.getCode());
		}
	

		finder0.append(" and bean.orgId=:orgId ");
		finder0.setParam("orgId",orgId);

		Pagination<FyTestVersion> p = testRecordDao.findPageByFinder(finder0, pageNo, pageSize);
		if (p.getList().size() > 0) {
			// TODO Auto-generated method stub
			Finder finder = Finder.create(" select bean.code from FyTestVersion bean where 1=1");
			finder0.append(" and bean.orgId=:orgId ");
			finder0.setParam("orgId",orgId);
			if (search.getTitle() != null) {
				finder.append(" and bean.title like:title");
				finder.setParam("title", "%" + search.getTitle() + "%");
			}

			finder.append(" group by bean.code ");
			finder.append(" order by bean.id desc");

			return testRecordDao.findCodesByFinder(finder, pageNo, pageSize);
		} else {
			return null;
		}
	}

	@Override
	public FyTestVersion update(FyTestVersion testRecord) {
		// TODO Auto-generated method stub
		Updater u = new Updater(testRecord);
		return testRecordDao.updateByUpdater(u);
	}

	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			testRecordDao.delete(id);
		}
	}

	
	

	
	private void checkAsk(FyQuestion q, String a) {
		// TODO Auto-generated method stub
		List<FyQuestionItem> is = q.getItems();
		is.get(0).setAnswer(a);
		q.setJson(JSONObject.toJSONString(is));

	}

	private void checkFill(FyQuestion q, String[] a) {
		// TODO Auto-generated method stub
		List<FyQuestionItem> is = q.getItems();
		Double avg = 0.0;
		if (!q.getIsQuestionnaire()) {
			avg = q.getScore() != 0 ? q.getScore() / is.size() : 0;
		}

		for (int i = 0; i < is.size(); i++) {
			is.get(i).setAnswer(a[i]);
			if (!q.getIsQuestionnaire()) {
				if (a[i].equals(is.get(i).getContent())) {
					q.setGoal(avg + (q.getGoal() != null ? q.getGoal() : 0.0));
				}
			}

		}
		q.setJson(JSONObject.toJSONString(is));
		if (!q.getIsQuestionnaire()) {
			q.setGoal(0.0);
			q.setIsGrade(true);
		}

	}

	private void checkRadio(FyQuestion q, Integer a) {
		List<FyQuestionItem> is = q.getItems();
		int o = 0;
		for (int i = 0; i < is.size(); i++) {
			if (i == a) {
				is.get(i).setIsAnswer(true);
			} else {
				is.get(i).setIsAnswer(false);
			}
			if (!q.getIsQuestionnaire() && is.get(i).getIsSolution()) {
				o = i;
			}

		}
		q.setJson(JSONObject.toJSONString(is));
		if (!q.getIsQuestionnaire()) {
			q.setGoal(a == o ? q.getScore() : 0.0);
			q.setIsGrade(true);
		}
	}

	private void checkCheckBox(FyQuestion q, Integer[] as) {
		List<FyQuestionItem> is = q.getItems();
		List<Integer> x = new ArrayList<Integer>();
		Arrays.sort(as);
		for (int i = 0; i < is.size(); i++) {
			if (ArrayUtils.contains(as, i)) {
				is.get(i).setIsAnswer(true);
			} else {
				is.get(i).setIsAnswer(false);
			}
			if (is.get(i).getIsSolution()) {
				x.add(i);
			}

		}
		q.setJson(JSONObject.toJSONString(is));
		if (!q.getIsQuestionnaire()) {
			q.setGoal(x.toArray(new Integer[x.size()]).equals(as) ? q.getGoal() : 0.0);
			q.setIsGrade(true);
		}
	}

	
	@Override
	public FyTestVersion findByCode(String code) {
		// TODO Auto-generated method stub
		return testRecordDao.findByCode(code);
	}

	@Override
	public FyTestVersion save(FyTestVersion vr) {
		// TODO Auto-generated method stub
		return testRecordDao.save(vr);
	}

	

}
