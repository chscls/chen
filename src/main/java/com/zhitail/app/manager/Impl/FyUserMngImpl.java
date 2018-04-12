package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyUser.Type;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class FyUserMngImpl implements FyUserMng{
	@Autowired
	private FyUserDao userDao;
	public Pagination<FyUser> getPage(Type type, Integer pageNo,
			Integer pageSize, FyUser search) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Finder finder = Finder.create(" from KfUser bean where 1=1");
				if(type!=null){
					finder.append(" and bean.type=:type ");
					finder.setParam("type", type);
				}
				if(search.getMobile()!=null){
					  finder.append(" and bean.mobile like:mobile");
			            finder.setParam("mobile","%"+search.getMobile()+"%");
				}
				if(search.getOpenid()!=null){
					  finder.append(" and bean.openid like:openid");
			            finder.setParam("openid","%"+search.getOpenid()+"%");
				}
				finder.append(" and bean.isDel=false order by bean.id desc");
				return userDao.findPageByFinder(finder, pageNo, pageSize);
	}

}
