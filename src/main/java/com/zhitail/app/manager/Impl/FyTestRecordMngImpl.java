package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
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
import com.zhitail.app.entity.middle.FyQuestionItem;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestRecordMngImpl implements FyTestRecordMng{
	@Autowired
	private FyTestRecordDao testRecordDao;
	@Autowired
	private FyTestMng testMng;
	@Override
	public FyTestRecord findById(Long id) {
		// TODO Auto-generated method stub
		return testRecordDao.findOne(id);
	}
	@Override
	public Pagination<String> getPage(Integer pageNo, Integer pageSize,
			FyTestRecord search) {
		Finder finder0 = Finder.create(" from FyTestRecord bean where 1=1");
	
		if (search.getTitle() != null) {
			finder0.append(" and bean.title like:title");
			finder0.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search.getCode()!= null) {
			finder0.append(" and bean.code =:code");
			finder0.setParam("code",search.getCode());
		}
		if (search.getTeaId()!= null) {
			finder0.append(" and bean.teaId =:teaId");
			finder0.setParam("teaId",search.getTeaId());
		}
		if (search.getUserId()!= null) {
			finder0.append(" and bean.userId =:userId");
			finder0.setParam("userId",search.getUserId());
		}
		
//		/finder0.append(" group by bean.orgId ");
		//finder0.append(" order by bean.id desc");
		
		Pagination<FyTestRecord> p = testRecordDao.findPageByFinder(finder0, pageNo, pageSize);
		if(p.getList().size()>0){
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" select bean.code from FyTestRecord bean where 1=1");
		finder.append(" and bean.teaId=:teaId");
		finder.setParam("teaId",search.getTeaId());
		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		
		finder.append(" group by bean.code ");
		finder.append(" order by bean.id desc");
		
		return testRecordDao.findCodesByFinder(finder, pageNo, pageSize);
		}else{
			return null;
		}
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
	public List<FyTestRecordStatistics> groupByCodes(String[] codes) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" select new com.zhitail.app.entity.middle.FyTestRecordStatistics(bean.code,count(bean.orgId),max(bean.score),min(bean.score),avg(bean.score)) from FyTestRecord bean where 1=1");

		if (codes!= null) {
			finder.append(" and bean.code in:codes");
			finder.setParamList("codes", codes);
		}
		finder.append(" group by bean.code ");
		finder.append(" order by bean.id desc");
		List<Object> list = testRecordDao.findObjectListByFinder(finder);
		List<FyTestRecordStatistics> xx=new ArrayList<FyTestRecordStatistics>();
		for(Object s:list){
			xx.add((FyTestRecordStatistics)s);
		}
		return xx;
		
	}
	@Override
	public Pagination<FyTestRecord> getDetailPage(Integer pageNo,
			Integer pageSize, FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (search!=null&&search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search!=null&&search.getCode()!= null) {
			finder.append(" and bean.code =:code");
			finder.setParam("code",search.getCode());
		}
		if (search!=null&&search.getTeaId()!= null) {
			finder.append(" and bean.teaId =:teaId");
			finder.setParam("teaId",search.getTeaId());
		}
		if (search!=null&&search.getUserId()!= null) {
			finder.append(" and bean.userId =:userId");
			finder.setParam("userId",search.getUserId());
		}
		
		finder.append(" order by bean.id desc");
		
		return testRecordDao.findPageByFinder(finder, pageNo, pageSize);
	}
	@Override
	public List<FyTestRecord> getList( Integer start, Integer count, FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (search!=null&&search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search!=null&&search.getCode()!= null) {
			finder.append(" and bean.code =:code");
			finder.setParam("code",search.getCode());
		}
		if (search!=null&&search.getTeaId()!= null) {
			finder.append(" and bean.teaId =:teaId");
			finder.setParam("teaId",search.getTeaId());
		}
		if (search!=null&&search.getUserId()!= null) {
			finder.append(" and bean.userId =:userId");
			finder.setParam("userId",search.getUserId());
		}
		finder.setFirstResult(start);
		finder.setMaxResults(count);
		
		finder.append(" order by bean.id desc");
		return testRecordDao.findListByFinder(finder);
	}
	@Override
	public FyTestRecord addTestRecord(Long userId, String code,Long recordId) {
		if(recordId!=null) {
			return  findById(recordId);
		}
		// TODO Auto-generated method stub
		FyTest t = testMng.findByCode(code);
		FyTestRecord tr=new FyTestRecord();
		tr.setCode(t.getCode());
		tr.setCreateTime(new Date());
		tr.setIsQuestionnaire(t.getIsQuestionnaire());
		tr.setLimitSecond(t.getLimitSecond());
		tr.setTitle(t.getTitle());
		tr.setTeaId(t.getUserId());
		tr.setMode(t.getMode());
		tr.setUserId(userId);
		tr.setStatus(Status.create);
		tr.setUuid(UUID.randomUUID().toString());
		tr.setJson(JSONArray.toJSONString(t.getQuestions()));
		return testRecordDao.save(tr);
	}
	@Override
	public Integer getTotal( FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");
		
		if (search!=null&&search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search!=null&&search.getCode()!= null) {
			finder.append(" and bean.code =:code");
			finder.setParam("code",search.getCode());
		}
		if (search!=null&&search.getTeaId()!= null) {
			finder.append(" and bean.teaId =:teaId");
			finder.setParam("teaId",search.getTeaId());
		}
		if (search!=null&&search.getUserId()!= null) {
			finder.append(" and bean.userId =:userId");
			finder.setParam("userId",search.getUserId());
		}
		int totalCount = testRecordDao.countQueryResult(finder);
		return totalCount;
	}
	@Override
	public FyTestRecord submit(Long id, String answers) {
		// TODO Auto-generated method stub
		FyTestRecord ftr=this.findById(id);
		JSONArray ja = JSONArray.parseArray(answers);
		List<FyQuestion> qs = ftr.getQuestions();
		FyQuestion q;
		for(int i=0;i<qs.size();i++) {
			q=qs.get(i);
		
			if(q.getType()==Type.single||q.getType()==Type.judge) {
				Integer a=ja.getInteger(i);
				checkRadio(q,a);
			}else if(q.getType()==Type.mutiply) {
			JSONArray temp=	ja.getJSONArray(i);
				Integer[] a=temp.toArray(new Integer[temp.size()]);
				checkCheckBox(q,a);
			}else if(q.getType()==Type.fill) {
				JSONArray temp=	ja.getJSONArray(i);
				String[] a=temp.toArray(new String[temp.size()]);
				checkFill(q,a);
			}else {
				String a=ja.getString(i);
				checkAsk(q,a);
			}
		}
		
		
		checkGoal(qs,ftr);
		return update(ftr);
	}
		
		private void checkGoal(List<FyQuestion> qs,FyTestRecord ftr) {
			Boolean isAllGrade=true;
			Double allGoal=0.0;
			for(FyQuestion q :qs) {
				if(!q.getIsGrade()) {
					if(isAllGrade) {
						isAllGrade=false;
					
					}
					
				}
				if(q.getGoal()!=null){
					allGoal=allGoal+q.getGoal();
				}
				
			}
			ftr.setGoal(allGoal);
			if(isAllGrade) {
				ftr.setStatus(Status.complete);
			}
			
			ftr.setJson(JSONObject.toJSONString(qs));
		// TODO Auto-generated method stub
		
		}
		private void checkAsk(FyQuestion q, String a) {
		// TODO Auto-generated method stub
			List<FyQuestionItem> is=q.getItems();
			is.get(0).setAnswer(a);
			q.setJson(JSONObject.toJSONString(is));
		
	}
		private void checkFill(FyQuestion q, String[] a) {
		// TODO Auto-generated method stub
			List<FyQuestionItem> is=q.getItems();
			Double avg=0.0;
			if(!q.getIsQuestionnaire()) {
				 avg = q.getScore()!=0?q.getScore()/is.size():0;
			}
			
			for(int i=0;i<is.size();i++) {
				is.get(i).setAnswer(a[i]);
				if(!q.getIsQuestionnaire()){
				if(a[i].equals(is.get(i).getContent())){
					q.setGoal(avg+(q.getGoal()!=null?q.getGoal():0.0));
				}
				}
			
			}
			q.setJson(JSONObject.toJSONString(is));
			if(!q.getIsQuestionnaire()){
			q.setGoal(0.0);
			q.setIsGrade(true);
			}
		
	}
		private void checkRadio(FyQuestion q,Integer a) {
			List<FyQuestionItem> is=q.getItems();
			int o=0;
			for(int i=0;i<is.size();i++) {
				if(i==a) {
					is.get(i).setIsAnswer(true);
				}else {
					is.get(i).setIsAnswer(false);
				}
				if(!q.getIsQuestionnaire()&&is.get(i).getIsSolution()) {
					o=i;
				}
				
				
			}
			q.setJson(JSONObject.toJSONString(is));
			if(!q.getIsQuestionnaire()){
			q.setGoal(a==o?q.getScore():0.0);
			q.setIsGrade(true);
			}
		}
		private void checkCheckBox(FyQuestion q,Integer[] as) {
			List<FyQuestionItem> is=q.getItems();
			List<Integer> x = new ArrayList<Integer>();
			Arrays.sort(as);
			for(int i=0;i<is.size();i++) {
				if(ArrayUtils.contains(as,i)) {
					is.get(i).setIsAnswer(true);
				}else {
					is.get(i).setIsAnswer(false);
				}
				if(is.get(i).getIsSolution()) {
					x.add(i);
				}
				
				
			}
			q.setJson(JSONObject.toJSONString(is));
			if(!q.getIsQuestionnaire()){
			q.setGoal(x.toArray(new Integer[x.size()]).equals(as)?q.getGoal():0.0);
			q.setIsGrade(true);
			}
		}
		@Override
		public FyTestRecord findByUuid(String uuid) {
			// TODO Auto-generated method stub
			return testRecordDao.findByUuid(uuid);
		}
		
	
}
