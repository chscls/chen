package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyGroupDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyGroup;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyGroupMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyGroupMngImpl implements FyGroupMng{
	@Autowired
	private FyGroupDao groupDao;
	public Pagination<FyGroup> getPage(Integer pageNo, Integer pageSize,
			FyGroup search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyGroup bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:name");
	            finder.setParam("name","%"+search.getName()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return groupDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyGroup save(FyGroup group) {
		// TODO Auto-generated method stub
		return groupDao.save(group);
	}

	public FyGroup update(FyGroup group) {
		// TODO Auto-generated method stub
		Updater u = new Updater(group);
	    return groupDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			groupDao.delete(id);	
			}
		
	}

	@Override
	public FyGroup findById(Long groupId) {
		// TODO Auto-generated method stub
		return groupDao.findOne(groupId);
	}

}
