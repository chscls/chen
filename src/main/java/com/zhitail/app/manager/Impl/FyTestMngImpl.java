package com.zhitail.app.manager.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
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
		String json =JSONArray.toJSONString(qids);
		t.setJson(json.substring(1, json.length()-1)+",");
		t.refreshCode();
		return update(t);
	}

	@Override
	public List<FyTest> findByCodes(String[] codes) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (codes!= null) {
			finder.append(" and bean.code in:codes");
			finder.setParamList("codes", codes );
		}

		finder.append(" order by bean.id desc");
		return testDao.findListByFinder(finder);
	}

	@Override
	public List<FyTest> getList(Integer start, Integer count, FyTest search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (search.getTitle() != null) {
			finder.append(" and bean.title like:title");
			finder.setParam("title", "%" + search.getTitle() + "%");
		}
		if (search.getUserId() != null) {
			finder.append(" and bean.userId=:userId");
			finder.setParam("userId",search.getUserId() );
		}
		if (search.getIsSale()!= null) {
			finder.append(" and bean.isSale=:isSale");
			finder.setParam("isSale",search.getIsSale() );
		}
		finder.setFirstResult(start);
		finder.setMaxResults(count);
		
		finder.append(" order by bean.id desc");
		return testDao.findListByFinder(finder);
	}

	@Override
	public FyTest findByCode(Long code) {
		// TODO Auto-generated method stub
		return testDao.findByCode(code);
	}

	@Override
	public List<FyTest> findByQuestionId(Long id) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyTest bean where 1=1");

		if (id != null) {
			finder.append(" and bean.json like:id");
			finder.setParam("id", "%" + id + ",%");
		}
		return testDao.findListByFinder(finder);
	}
}
