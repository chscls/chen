package com.zhitail.app.manager.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyQuestionDao;
import com.zhitail.app.dao.FyTestDao;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyTestMngImpl implements FyTestMng {
	@Autowired
	private FyTestDao testDao;
	@Autowired
	private FyQuestionMng questionMng;
	public Pagination<FyTest> getPage(Long userId,Integer pageNo, Integer pageSize,
			FyTest search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		finder.append(" and bean.userId=:userId");
		finder.setParam("userId",userId);
		finder.append(" order by bean.id desc");
		return testDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyTest update(FyTest test) {
		// TODO Auto-generated method stub
		Updater u = new Updater(test);
		return testDao.updateByUpdater(u);
	}

	public FyTest save(FyTest test) {
		// TODO Auto-generated method stub
		return testDao.save(test);
	}

	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			testDao.delete(id);
		}
	}

	@Override
	public FyTest findById(Long id) {
		// TODO Auto-generated method stub
		return testDao.findOne(id);
	}

	@Override
	public FyTest updateTestQuestions(Long id, Long[] qids) {
		FyTest  t= testDao.findOne(id);
		// TODO Auto-generated method stub
		List<FyQuestion>  list =t.getQuestions();
		for(Long qid:qids){
		list.add(questionMng.findById(qid));
		}
		
		return update(t);
	}

	@Override
	public List<FyTest> findByIds(Long[] ids) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (ids != null) {
			finder.append(" and bean.id in:ids");
			finder.setParamList("ids", ids );
		}

		finder.append(" order by bean.id desc");
		return testDao.findListByFinder(finder);
	}

	@Override
	public List<FyTest> getList(Long userId, Integer start, Integer count, FyTest search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		finder.setFirstResult(start);
		finder.setMaxResults(count);
		finder.append(" and bean.userId=:userId");
		finder.setParam("userId",userId);
		finder.append(" order by bean.id desc");
		return testDao.findListByFinder(finder);
	}
}
