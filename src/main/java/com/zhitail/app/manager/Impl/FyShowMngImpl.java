package com.zhitail.app.manager.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyShowDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyShow;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyShowMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyShowMngImpl implements FyShowMng{
	@Autowired
	private FyShowDao showDao;
	public Pagination<FyShow> getPage(Integer pageNo, Integer pageSize,
			FyShow search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyShow bean where 1=1");
	
		if(search.getName()!=null){
			  finder.append(" and bean.name like:word");
	            finder.setParam("word","%"+search.getName()+"%");
		}
		 finder.append(" and bean.parentId = NULL");
      
		finder.append(" order by bean.id desc");
		return showDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyShow save(FyShow bean) {
		bean = showDao.save(bean);
		if(bean.getParentId()!=null) {
			FyShow c = showDao.findOne(bean.getParentId());
			List<FyShow> fc = c.getChildren();
			if(fc!=null) {
				fc.add(bean);
		
			}
		}
		// TODO Auto-generated method stub
		return showDao.save(bean);
	}

	public FyShow update(FyShow bean) {
		// TODO Auto-generated method stub
		Updater u = new Updater(bean);
	    return showDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			showDao.delete(id);	
			}
		
	}

}
