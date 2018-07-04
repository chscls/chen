package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FySkinDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FySkin;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FySkinMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FySkinMngImpl implements FySkinMng{
	@Autowired
	private FySkinDao skinDao;
	public Pagination<FySkin> getPage(Integer pageNo, Integer pageSize,
			FySkin search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FySkin bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:name");
	            finder.setParam("word","%"+search.getName()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return skinDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FySkin save(FySkin bean) {
		// TODO Auto-generated method stub
		return skinDao.save(bean);
	}

	public FySkin update(FySkin bean) {
		// TODO Auto-generated method stub
		Updater u = new Updater(bean);
	    return skinDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			skinDao.delete(id);	
			}
		
	}

}
