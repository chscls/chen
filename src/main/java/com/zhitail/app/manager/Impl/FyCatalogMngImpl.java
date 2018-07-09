package com.zhitail.app.manager.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.dao.FyCatalogDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyCatalog;
import com.zhitail.app.entity.FyShow;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyShow.Type;
import com.zhitail.app.manager.FyCatalogMng;
import com.zhitail.app.manager.FyShowMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyCatalogMngImpl implements FyCatalogMng {
	@Autowired
	private FyCatalogDao catalogDao;
	@Autowired
	private FyShowMng showMng;

	public Pagination<FyCatalog> getPage(Integer pageNo, Integer pageSize, FyCatalog search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyCatalog bean where 1=1");

		if (search.getName() != null) {
			finder.append(" and bean.name like:word");
			finder.setParam("word", "%" + search.getName() + "%");
		}
		finder.append(" and bean.parentId = NULL");

		finder.append(" order by bean.id desc");
		return catalogDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyCatalog save(FyCatalog bean) {
		bean = catalogDao.save(bean);
		if (bean.getParentId() != null) {
			FyCatalog c = catalogDao.findOne(bean.getParentId());
			List<FyCatalog> fc = c.getChildren();
			if (fc != null) {
				fc.add(bean);

			}
		}
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
			
				List<FyShow> fts = showMng.findByCatalogId(id);
				for(FyShow ft:fts) {
				List<Long> qids = ft.getShowIds();
				qids.remove(id);

				ft.setJson(JSONArray.toJSONString(qids));
				
				showMng.update(ft);
				}
			
			}
		
	}

	@Override
	public List<FyCatalog> findByIds(Long[] ids) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyCatalog bean where 1=1");
		if (ids != null) {
			finder.append(" and bean.id in:ids ");
			finder.setParamList("ids", ids);
		}
		return catalogDao.findListByFinder(finder);
	}

	

}
