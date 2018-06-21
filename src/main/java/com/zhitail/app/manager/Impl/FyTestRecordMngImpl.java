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
import com.zhitail.app.dao.FyTestRecordDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FyTestRecord.Status;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.middle.FyAnswer;
import com.zhitail.app.entity.middle.FyQuestionItem;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FyTestVersionMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestRecordMngImpl implements FyTestRecordMng {
	@Autowired
	private FyTestRecordDao testRecordDao;
	@Autowired
	private FyTestMng testMng;
	
	@Autowired
	private FyTestVersionMng versionMng;
	@Override
	public FyTestRecord findById(Long id) {
		// TODO Auto-generated method stub
		return testRecordDao.findOne(id);
	}

	/*@Override
	public Pagination<String> getPage(Integer pageNo, Integer pageSize, FyTestRecord search,Long orgId) {
		Finder finder0 = Finder.create(" from FyTestRecord bean where 1=1");

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

		Pagination<FyTestRecord> p = testRecordDao.findPageByFinder(finder0, pageNo, pageSize);
		if (p.getList().size() > 0) {
			// TODO Auto-generated method stub
			Finder finder = Finder.create(" select bean.code from FyTestRecord bean where 1=1");
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
	}*/

	@Override
	public FyTestRecord update(FyTestRecord testRecord) {
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

	@Override
	public List<FyTestRecordStatistics> groupByCodes(String[] codes) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(
				" select new com.zhitail.app.entity.middle.FyTestRecordStatistics(bean.version.score,bean.version.updateTime,bean.version.orgId,bean.version.code,bean.version.title,count(bean.id),max(bean.goal),min(bean.goal),avg(bean.goal),bean.version.mode) from FyTestRecord bean where 1=1 ");

		if (codes != null) {
			finder.append(" and bean.version.code in:codes");
			finder.setParamList("codes", codes);
		}
		finder.append(" group by bean.version.code ");
		finder.append(" order by bean.id desc");
		List<Object> list = testRecordDao.findObjectListByFinder(finder);
		List<FyTestRecordStatistics> xx = new ArrayList<FyTestRecordStatistics>();
		for (Object s : list) {
			xx.add((FyTestRecordStatistics) s);
		}
		return xx;

	}

	@Override
	public Pagination<FyTestRecord> getDetailPage(Integer pageNo, Integer pageSize, FyTestRecord search,FyTestVersion version,Long[] ids,String sort) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (version != null && version.getTitle() != null) {
			finder.append(" and bean.version.title like:title");
			finder.setParam("title", "%" + version.getTitle() + "%");
		}
		if (version != null && version.getCode() != null) {
			finder.append(" and bean.version.code=:code");
			finder.setParam("code", version.getCode());
		}
		if (version != null && version.getTeaId() != null) {
			finder.append(" and bean.version.teaId=:teaId");
			finder.setParam("teaId", version.getTeaId());
		}
		if (search != null && search.getUserId() != null) {
			finder.append(" and bean.userId=:userId");
			finder.setParam("userId", search.getUserId());
		}
		if (search != null && search.getStatus()!= null) {
			finder.append(" and bean.status=:status");
			finder.setParam("status", search.getStatus());
		}
		if(ids!=null) {
			finder.append(" and bean.userId in:ids");
			finder.setParamList("ids", ids);
		}
		finder.append(" order by bean.goal "+sort);

		return testRecordDao.findPageByFinder(finder, pageNo, pageSize);
	}

	@Override
	public List<FyTestRecord> getList(Integer start, Integer count,FyTestVersion version, FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (version!= null && version.getTitle() != null) {
			finder.append(" and bean.version.title like:title");
			finder.setParam("title", "%" + version.getTitle() + "%");
		}
		if (version != null && version.getCode() != null) {
			finder.append(" and bean.code=:code");
			finder.setParam("code", version.getCode());
		}
		if (version != null && version.getTeaId() != null) {
			finder.append(" and bean.version.teaId=:teaId");
			finder.setParam("teaId", version.getTeaId());
		}
		if (search != null && search.getStatus()!= null) {
			finder.append(" and bean.status=:status");
			finder.setParam("status", search.getStatus());
		}
		if (search != null && search.getUserId() != null) {
			finder.append(" and bean.userId=:userId");
			finder.setParam("userId", search.getUserId());
		}
		finder.setFirstResult(start);
		finder.setMaxResults(count);

		finder.append(" order by bean.id desc");
		return testRecordDao.findListByFinder(finder);
	}

	

	@Override
	public FyTestRecord addTestRecord(Long userId, String code, Long recordId) {
		if (recordId != null) {
			return findById(recordId);
		}

		// TODO Auto-generated method stub
		FyTest t = testMng.findByCode(code);
		
		FyTestVersion vr = versionMng.findByCode(code);
		FyTestRecord tr = new FyTestRecord();
		if(vr==null) {
		
			List<QuestionConfig> qcs = t.getQuestionConfigs();
			double score=0.0;
			for(QuestionConfig qc:qcs) {
				score+=qc.getScore();
			}
			
			vr.setCode(t.getCode());
			
			vr.setIsQuestionnaire(t.getIsQuestionnaire());
			vr.setLimitSecond(t.getLimitSecond());
			vr.setTitle(t.getTitle());
			vr.setTeaId(t.getUserId());
			vr.setMode(t.getMode());
			
			vr.setOrgId(t.getId());
			vr.setScore(score);
			vr.setUpdateTime(t.getUpdateTime());
			testMng.fullQuestions(t);
			vr.setJson(JSONArray.toJSONString(t.getQuestions()));
			vr = versionMng.save(vr);
		}
		tr.setTestVersion(vr);
		tr.setCreateTime(new Date());
		tr.setUserId(userId);
		tr.setStatus(Status.create);
		tr.reFreshUuid();
		return testRecordDao.save(tr);
	}

	@Override
	public Integer getTotal(FyTestVersion vr,FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (vr != null && vr.getTitle() != null) {
			finder.append(" and bean.version.title like:title");
			finder.setParam("title", "%" + vr.getTitle() + "%");
		}
		if (vr != null && vr.getCode() != null) {
			finder.append(" and bean.version.code =:code");
			finder.setParam("code", vr.getCode());
		}
		if (vr != null && vr.getTeaId() != null) {
			finder.append(" and bean.version.teaId =:teaId");
			finder.setParam("teaId", vr.getTeaId());
		}
		if (search != null && search.getUserId() != null) {
			finder.append(" and bean.userId =:userId");
			finder.setParam("userId", search.getUserId());
		}
		int totalCount = testRecordDao.countQueryResult(finder);
		return totalCount;
	}

	@Override
	public FyTestRecord submit(Long id, String answers,String sign) {
		// TODO Auto-generated method stub
		FyTestRecord ftr = this.findById(id);
		if(StringUtils.isNotBlank(sign)) {
		ftr.setSign(sign);
		}
		List<FyAnswer> ans = JSONArray.parseArray(answers, FyAnswer.class);
		List<FyQuestion> qs = ftr.getTestVersion().getQuestions();
		if(ans.size()!=qs.size()) {
			return null;
		}
		
		FyQuestion q;
		FyAnswer a;
		if(!ftr.getTestVersion().getIsQuestionnaire()) {
			for (int i = 0; i < qs.size(); i++) {
			q = qs.get(i);
			
				a = ans.get(i);
				if (q.getType() == Type.single || q.getType() == Type.judge) {
				
				checkRadio(q, a);
			    } else if (q.getType() == Type.mutiply) {
			
				checkCheckBox(q, a);
			   } else if (q.getType() == Type.fill) {
				
				checkFill(q, a);
			   } else {
				
				checkAsk(q, a);
			   }
			}
			checkGoal(ans, ftr);
		}else {
			ftr.setStatus(Status.complete);
			ftr.setEndTime(new Date());
			ftr.setJson(JSONObject.toJSONString(ans));
		}
		
	
		return update(ftr);
	}

	private void checkGoal(List<FyAnswer> ans, FyTestRecord ftr) {
		Boolean isAllGrade = true;
		Double allGoal = 0.0;
		for (FyAnswer q : ans) {
			if (!q.getIsGrade()) {
				if (isAllGrade) {
					isAllGrade = false;

				}

			}
			if (q.getGoal() != null) {
				allGoal = allGoal + q.getGoal();
			}

		}
		ftr.setGoal(allGoal);
		if (isAllGrade) {
			ftr.setStatus(Status.complete);
			ftr.setEndTime(new Date());
		}else {
			ftr.setStatus(Status.check);
		}

		ftr.setJson(JSONObject.toJSONString(ans));
		// TODO Auto-generated method stub

	}

	private void checkAsk(FyQuestion q, FyAnswer a) {
		// TODO Auto-generated method stub
		return;
	}

	private void checkFill(FyQuestion q, FyAnswer a) {
		// TODO Auto-generated method stub
		List<FyQuestionItem> is = q.getItems();
		Double avg = q.getScore() != 0 ? q.getScore() / is.size() : 0;	
		a.setGoal(0.0);
		for (int i = 0; i < is.size(); i++) {
			if (a.getAnswers()[i].equals(is.get(i).getContent())) {
				a.setGoal(avg + (a.getGoal() != null ? a.getGoal() : 0.0));
			}
		}
		a.setIsGrade(true);
	}

	private void checkRadio(FyQuestion q, FyAnswer a) {
			a.setGoal(q.getItems().get(a.getIndex()[0]).getIsAnswer()?q.getScore() : 0.0);
			a.setIsGrade(true);	
	}

	private void checkCheckBox(FyQuestion q, FyAnswer as) {
		List<FyQuestionItem> is = q.getItems();
		Integer[] indexs = as.getIndex();
		List<Integer> x = new ArrayList<Integer>();
		Arrays.sort(indexs);
		for (int i = 0; i < is.size(); i++) {
			if (is.get(i).getIsSolution()) {
				x.add(i);
			}
		}
	
		
		q.setGoal(x.toArray(new Integer[x.size()]).equals(indexs) ? q.getGoal() : 0.0);
		q.setIsGrade(true);
		
	}

	@Override
	public FyTestRecord findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return testRecordDao.findByUuid(uuid);
	}

	@Override
	public Pagination<FyTestRecord> getMyPage(Integer pageNo, Integer pageSize,FyTestVersion vr,FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (vr != null && vr.getTitle() != null) {
			finder.append(" and bean.version.title like:title");
			finder.setParam("title", "%" + vr.getTitle() + "%");
		}
		if (vr != null && vr.getCode() != null) {
			finder.append(" and bean.version.code =:code");
			finder.setParam("code", vr.getCode());
		}
		if (vr != null && vr.getTeaId() != null) {
			finder.append(" and bean.version.teaId =:teaId");
			finder.setParam("teaId", vr.getTeaId());
		}
		if (search.getUserId() != null) {
			finder.append(" and bean.userId =:userId");
			finder.setParam("userId", search.getUserId());
		}
		if (vr.getOrgId() != null) {
			finder.append(" and bean.version.orgId =:orgId");
			finder.setParam("userId", vr.getOrgId());
		}
		finder.append(" order by bean.id desc");
		return testRecordDao.findPageByFinder(finder, pageNo, pageSize);
	}

	

}
