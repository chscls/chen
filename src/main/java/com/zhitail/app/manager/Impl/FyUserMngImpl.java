package com.zhitail.app.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;








import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyUser.Type;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
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
				Finder finder = Finder.create(" from FyUser bean where 1=1");
				if(type!=null){
					finder.append(" and bean.type=:type ");
					finder.setParam("type", type);
				}
				if(search.getRealname()!=null){
					  finder.append(" and bean.realname like:realname");
			            finder.setParam("realname","%"+search.getRealname()+"%");
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
	public FyUser update(FyUser user) {
		// TODO Auto-generated method stub
		Updater u = new Updater(user);
	    return userDao.updateByUpdater(u);
	}
	public FyUser save(FyUser user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for(Long id:ids){
		FyUser u = 	userDao.findOne(id);
		u.setIsDel(true);
		this.update(u);
		}
	}
}
