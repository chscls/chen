package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyAdDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyAd;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyAdMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyAdMngImpl implements FyAdMng{
	@Autowired
	private FyAdDao sensitiveDao;
	public Pagination<FyAd> getPage(Integer pageNo, Integer pageSize,
			FyAd search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyAd bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:word");
	            finder.setParam("word","%"+search.getName()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return sensitiveDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyAd save(FyAd bean) {
		// TODO Auto-generated method stub
		return sensitiveDao.save(bean);
	}

	public FyAd update(FyAd bean) {
		// TODO Auto-generated method stub
		Updater u = new Updater(bean);
	    return sensitiveDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			sensitiveDao.delete(id);	
			}
		
	}

}
