package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyAdSpaceDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyAdSpace;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyAdSpaceMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyAdSpaceMngImpl implements FyAdSpaceMng{
	@Autowired
	private FyAdSpaceDao sensitiveDao;
	public Pagination<FyAdSpace> getPage(Integer pageNo, Integer pageSize,
			FyAdSpace search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyAdSpace bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:name");
	            finder.setParam("name","%"+search.getName()+"%");
		}
		if(search.getKeyword()!=null){
			  finder.append(" and bean.keyword like:keyword");
	            finder.setParam("keyword","%"+search.getKeyword()+"%");
		}
		finder.append(" order by bean.id desc");
		return sensitiveDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyAdSpace save(FyAdSpace bean) {
		// TODO Auto-generated method stub
		return sensitiveDao.save(bean);
	}

	public FyAdSpace update(FyAdSpace bean) {
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
