package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyCatalogDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyCatalog;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyCatalogMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyCatalogMngImpl implements FyCatalogMng{
	@Autowired
	private FyCatalogDao catalogDao;
	public Pagination<FyCatalog> getPage(Integer pageNo, Integer pageSize,
			FyCatalog search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyCatalog bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:word");
	            finder.setParam("word","%"+search.getName()+"%");
		}
		 finder.append(" and bean.parentId = NULL");
      
		finder.append(" order by bean.id desc");
		return catalogDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyCatalog save(FyCatalog bean) {
		// TODO Auto-generated method stub
		return catalogDao.save(bean);
	}

	public FyCatalog update(FyCatalog bean) {
		// TODO Auto-generated method stub
		Updater u = new Updater(bean);
	    return catalogDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			catalogDao.delete(id);	
			}
		
	}

}
