package com.zhitail.app.manager.Impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyOrderDao;
import com.zhitail.app.dao.FyOrderDao;
import com.zhitail.app.entity.FyOrder;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyOrder;

import com.zhitail.app.manager.FyOrderMng;
import com.zhitail.app.manager.FyOrderMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyOrderMngImpl implements FyOrderMng{
	@Autowired
	private FyOrderDao orderDao;
	public Pagination<FyOrder> getPage(Long userId, Integer pageNo,
			Integer pageSize, String code,String title,String sorter) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Finder finder = Finder.create(" from FyOrder bean where 1=1");
				if (StringUtils.isNotBlank(title)) {
					finder.append(" and bean.title like:title");
					finder.setParam("title", "%" + title + "%");
				}
				if (StringUtils.isNotBlank(code)) {
					finder.append(" and bean.code like:code");
					finder.setParam("code", "%" + code + "%");
				}
				finder.append(" and bean.isDel=false ");
				finder.append(" and bean.userId=:userId ");
				
				finder.setParam("userId", userId);
				if(StringUtils.isNotBlank(sorter)&&sorter.equals("createTime_ascend")){
					finder.append(" order by bean.createTime asc");
				}else {
					finder.append(" order by bean.createTime desc");
				}
				return orderDao.findPageByFinder(finder, pageNo, pageSize);
	}
	public FyOrder update(FyOrder user) {
		// TODO Auto-generated method stub
		Updater u = new Updater(user);
	    return orderDao.updateByUpdater(u);
	}
	public FyOrder save(FyOrder user) {
		// TODO Auto-generated method stub
		user.setCreateTime(new Date());
		return orderDao.save(user);
	}

	public FyOrder findById(Long id) {
		// TODO Auto-generated method stub
		return orderDao.findOne(id);
	}

	@Override
	public List<FyOrder> findByIds(Long[] ids) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyOrder bean where 1=1");
		if(ids!=null){
			finder.append(" and bean.id in:ids ");
			finder.setParamList("ids", ids);
		}
		return orderDao.findListByFinder(finder);
	}
	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for(Long id:ids){
			FyOrder u = 	findById(id);
			u.setIsDel(true);
			this.update(u);
			}
	}


}
