package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.dao.FyTestDao;
import com.zhitail.app.dao.FyTestRecordDao;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
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
	public FyTestRecord addTestRecord(Long userId, String code) {
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

	
}
