package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyQuestionDao;
import com.zhitail.app.dao.FySensitiveDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyQuestionMngImpl implements FyQuestionMng{
	@Autowired
	private FyQuestionDao questionDao;
	public Pagination<FyQuestion> getPage(Integer pageNo, Integer pageSize,
			FyQuestion search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyQuestion bean where 1=1");
		
		if(search.getTitle()!=null){
			  finder.append(" and bean.word like:word");
	            finder.setParam("word","%"+search.getTitle()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return questionDao.findPageByFinder(finder, pageNo, pageSize);
	}
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for(Long id:ids){
			 questionDao.delete(id);
		}
		
	}
	public FyQuestion update(FyQuestion question) {
		// TODO Auto-generated method stub
		Updater u = new Updater(question);
	    return questionDao.updateByUpdater(u);
	}
	public FyQuestion save(FyQuestion question) {
		// TODO Auto-generated method stub
		return questionDao.save(question);
	}

}
