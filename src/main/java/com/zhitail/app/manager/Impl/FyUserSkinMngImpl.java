package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyUserSkinDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyUserSkin;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyUserSkinMng;
import com.zhitail.app.manager.FyUserSkinMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyUserSkinMngImpl implements FyUserSkinMng{
	@Autowired
	private FyUserSkinDao userSkinDao;
	public Pagination<FyUserSkin> getPage(Long userId,Integer pageNo, Integer pageSize,FyUserSkin search,String name,String code
			) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyUserSkin bean where 1=1");
	
		if(name!=null){
			  finder.append(" and bean.skin.name like:name");
	            finder.setParam("name","%"+name+"%");
		}
		if(code!=null){
			  finder.append(" and bean.skin.code like:code");
	            finder.setParam("code","%"+code+"%");
		}
		finder.append(" and bean.userId =:userId ");
		 finder.setParam("userId",userId);
		return userSkinDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyUserSkin save(FyUserSkin bean) {
		// TODO Auto-generated method stub
		return userSkinDao.save(bean);
	}

	public FyUserSkin update(FyUserSkin bean) {
		// TODO Auto-generated method stub
		Updater u = new Updater(bean);
	    return userSkinDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			userSkinDao.delete(id);	
			}
		
	}

}
