package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.zhitail.app.dao.FyTestVersionDao;
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
	private FyTestVersionDao testVersionDao;
	@Autowired
	private FyTestMng testMng;
	
	@Autowired
	private FyTestVersionMng versionMng;
	@Override
	public FyTestRecord findById(Long id) {
		// TODO Auto-generated method stub
		return testRecordDao.findOne(id);
	}

	@Override
	public Pagination<String> getPage(Integer pageNo, Integer pageSize, FyTestVersion search) {

			Finder finder = Finder.create(" select bean.code from  FyTestVersion bean where 1=1");
			finder.append(" and bean.orgId=:orgId ");
			finder.setParam("orgId",search.getOrgId());
			if (search.getCode() != null) {
				finder.append(" and bean.code =:code");
				finder.setParam("code", search.getCode());
			}
			if (search.getTitle() != null) {
				finder.append(" and bean.title like:title");
				finder.setParam("title", "%" + search.getTitle() + "%");
			}

			finder.append(" order by bean.id desc");

			return testVersionDao.findCodesByFinder(finder, pageNo, pageSize);
		
	}

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
	public Pagination<FyTestRecordStatistics> groupByCodes(FyTestVersion version,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(
				" select new com.zhitail.app.entity.middle.FyTestRecordStatistics(bean.version.score,bean.version.updateTime,bean.version.orgId,bean.version.code,bean.version.title,count(bean.id),max(bean.goal),min(bean.goal),avg(bean.goal),bean.version.mode) from FyTestRecord bean where 1=1 ");
		if (version.getCode() != null) {
			finder.append(" and bean.version.code =:code");
			finder.setParam("code", version.getCode());
		}
		if (version.getOrgId() != null) {
			finder.append(" and bean.version.orgId =:orgId");
			finder.setParam("orgId", version.getOrgId());
		}
		finder.append(" group by bean.version.code ");
		finder.append(" order by bean.id desc");
		//testRecordDao.findPageByFinder(finder, pageNo, pageSize);
		Pagination<Object> page= 	testRecordDao.findObjectPageByFinder(finder, pageNo, pageSize);
		Pagination<FyTestRecordStatistics> page2  =new Pagination<FyTestRecordStatistics>(pageNo, pageSize,page.getTotalCount());
		
		List<FyTestRecordStatistics> xx = new ArrayList<FyTestRecordStatistics>();
		for (Object s : page.getList()) {
			xx.add((FyTestRecordStatistics) s);
		}
		page2.setList(xx);
		return page2;

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
			finder.append(" and bean.version.code=:code");
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
	public FyTestRecord addTestRecord(Long userId, Long id, Long recordId) {
		if (recordId != null) {
			return findById(recordId);
		}

		// TODO Auto-generated method stub
		FyTest t = testMng.findById(id);
		
		FyTestVersion vr = versionMng.findByCode(t.getCode());
		FyTestRecord tr = new FyTestRecord();
		if(vr==null) {
			vr=new FyTestVersion();
			List<QuestionConfig> qcs = t.getQuestionConfigs();
			double score=0.0;
			for(QuestionConfig qc:qcs) {
				score+=qc.getScore();
			}
			
			vr.setCode(t.getCode());
			vr.setIsNoOrder(t.getIsNoOrder());
			vr.setIsQuestionnaire(t.getIsQuestionnaire());
			vr.setLimitSecond(t.getLimitSecond());
			vr.setTitle(t.getTitle());
			vr.setTeaId(t.getUserId());
			vr.setMode(t.getMode());
			vr.setCreateTime(new Date());
			vr.setOrgId(t.getId());
			vr.setScore(score);
			vr.setUpdateTime(t.getUpdateTime());
			testMng.fullQuestions(t);
			vr.setRate(computeRate(score,t.getQuestions()));
			vr.setJson(JSONArray.toJSONString(t.getQuestions()));
			vr = versionMng.save(vr);
		}
	
		tr.setVersion(vr);
		tr.setCreateTime(new Date());
		tr.setUserId(userId);
		tr.setStatus(Status.create);
	    tr.setJson(computeOrder(vr) );
		tr.reFreshUuid();
		return testRecordDao.save(tr);
	}

	private String computeOrder(FyTestVersion vr) {
		
		
		List<FyQuestion> qs=vr.getQuestions();
		List<FyAnswer> ans = new ArrayList<FyAnswer>(qs.size());
		FyAnswer a;
		for(int i=0;i<qs.size();i++) {
			a=new FyAnswer(i);
			if(qs.get(i).getType()==Type.single||qs.get(i).getType()==Type.mutiply||qs.get(i).getType()==Type.judge) {
				a.makeOrder(qs.get(i).getItems().size());		
			}
			ans.add(a);
		}
		if(vr.getIsNoOrder()) {
			Collections.shuffle(ans);
		}
		return JSONArray.toJSONString(ans);
	}

	private Double computeRate(Double score,List<FyQuestion> questions) {
		// TODO Auto-generated method stub
		Double total=0.0;
		for(FyQuestion q:questions) {
			total+=(q.getScore()*(100-q.getDifficulty()))/score;
		}
		return total/100;
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
		
		List<FyQuestion> qs = ftr.getQuestions();
		if(ans.size()!=qs.size()) {
			return null;
		}
		List<FyAnswer> ano = ftr.getAnswers();
		for(int i=0;i<ans.size();i++) {
			ans.get(i).setIndex(ano.get(i).getIndex());
			ans.get(i).setOrders(ano.get(i).getOrders());
		}
		FyQuestion q;
		FyAnswer a;
		if(!ftr.getVersion().getIsQuestionnaire()) {
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
		Boolean isSolution = q.getItems().get(a.getIndexs()[0]).getIsSolution();
			a.setGoal(isSolution!=null&&isSolution?q.getScore() : 0.0);
			a.setIsGrade(true);	
	}

	private void checkCheckBox(FyQuestion q, FyAnswer a) {
		List<FyQuestionItem> is = q.getItems();
		Integer[] indexs = a.getIndexs();
		List<Integer> x = new ArrayList<Integer>();
		Arrays.sort(indexs);
		for (int i = 0; i < is.size(); i++) {
			if (is.get(i).getIsSolution()) {
				x.add(i);
			}
		}
		Integer[] b=x.toArray(new Integer[x.size()]);
		Boolean  y = Arrays.equals(b,indexs);
		a.setGoal(y? q.getScore() : 0.0);
		a.setIsGrade(true);
		
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
