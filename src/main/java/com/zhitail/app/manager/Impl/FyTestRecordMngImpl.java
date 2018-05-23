package com.zhitail.app.manager.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyTestDao;
import com.zhitail.app.dao.FyTestRecordDao;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestRecordMngImpl implements FyTestRecordMng{
	@Autowired
	private FyTestRecordDao testRecordDao;
	@Override
	public FyTestRecord findById(Long id) {
		// TODO Auto-generated method stub
		return testRecordDao.findOne(id);
	}
	@Override
	public Pagination<Long> getPage(Integer pageNo, Integer pageSize,
			FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" select bean.orgId from FyTestRecord bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		finder.append(" group by bean.orgId ");
		finder.append(" order by bean.id desc");
		
		return testRecordDao.findIdsByFinder(finder, pageNo, pageSize);
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
	public List<FyTestRecordStatistics> groupByIds(Long[] orgIds) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" select new com.zhitail.app.entity.middle.FyTestRecordStatistics(bean.orgId,count(bean.orgId),max(bean.score),min(bean.score),avg(bean.score)) from FyTestRecord bean where 1=1");

		if (orgIds!= null) {
			finder.append(" and bean.orgId in:orgIds");
			finder.setParamList("orgIds", orgIds);
		}
		finder.append(" group by bean.orgId ");
		finder.append(" order by bean.id desc");
		List<Object> list = testRecordDao.findObjectListByFinder(finder);
		List<FyTestRecordStatistics> xx=new ArrayList<FyTestRecordStatistics>();
		for(Object s:list){
			xx.add((FyTestRecordStatistics)s);
		}
		return xx;
		
	}
	@Override
	public Pagination<FyTestRecord> getDetailPage( Integer pageNo,
			Integer pageSize, FyTestRecord search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTestRecord bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search.getOrgId()!= null) {
			finder.append(" and bean.orgId =:orgId");
			finder.setParam("orgId",search.getOrgId());
		}
		
		finder.append(" order by bean.id desc");
		
		return testRecordDao.findPageByFinder(finder, pageNo, pageSize);
	}

	
}
