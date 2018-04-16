package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FySensitiveDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FySensitiveMngImpl implements FySensitiveMng{
	@Autowired
	private FySensitiveDao sensitiveDao;
	public Pagination<FySensitive> getPage(Integer pageNo, Integer pageSize,
			FySensitive search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FySensitive bean where 1=1");
	
		if(search.getWord()!=null){
			  finder.append(" and bean.word like:word");
	            finder.setParam("word","%"+search.getWord()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return sensitiveDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FySensitive save(FySensitive sensitive) {
		// TODO Auto-generated method stub
		return sensitiveDao.save(sensitive);
	}

	public FySensitive update(FySensitive sensitive) {
		// TODO Auto-generated method stub
		Updater u = new Updater(sensitive);
	    return sensitiveDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			sensitiveDao.delete(id);	
			}
		
	}

}
