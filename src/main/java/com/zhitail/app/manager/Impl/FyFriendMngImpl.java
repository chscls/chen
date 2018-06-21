package com.zhitail.app.manager.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.app.dao.FyFriendDao;
import com.zhitail.app.dao.FyUserDao;
import com.zhitail.app.entity.FyFriend;
import com.zhitail.app.entity.FyGroup;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyFriendMng;
import com.zhitail.app.manager.FyGroupMng;
import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;

@Service
@Transactional
public class  FyFriendMngImpl implements FyFriendMng{
	@Autowired
	private FyFriendDao friendDao;
	
	@Autowired
	private FyGroupMng groupMng;
	public Pagination<FyFriend> getPage(Integer pageNo, Integer pageSize,
			FyFriend search) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyFriend bean where 1=1");
	
		if(search.getRealname()!=null){
			  finder.append(" and bean.realname like:realname");
	            finder.setParam("realname","%"+search.getRealname()+"%");
		}
		
		finder.append(" order by bean.id desc");
		return friendDao.findPageByFinder(finder, pageNo, pageSize);
	}

	public FyFriend save(FyFriend sensitive) {
		// TODO Auto-generated method stub
		return friendDao.save(sensitive);
	}

	public FyFriend update(FyFriend sensitive) {
		// TODO Auto-generated method stub
		Updater u = new Updater(sensitive);
	    return friendDao.updateByUpdater(u);
	}

	public void delete(Long[] ids) {
		for(Long id:ids){
			friendDao.delete(id);	
			}
		
	}

	@Override
	public FyFriend check(Long userId, Long friendId) {
		// TODO Auto-generated method stub
		return friendDao.check(userId, friendId);
	}

	@Override
	public List<FyFriend> findByIds(Long userId, Long[] fids) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create(" from FyFriend bean where 1=1");
		if(userId!=null){
			  finder.append(" and bean.userId =:userId");
	            finder.setParam("userId",userId);
		}
		if(fids!=null){
			  finder.append(" and bean.friendId in:fids");
	            finder.setParamList("fids",fids);
		}
		
		
		return friendDao.findListByFinder(finder);
	}

	@Override
	public void changeGroup(Long[] ids, Long groupId) {
		FyGroup group=groupMng.findById(groupId);
		// TODO Auto-generated method stub
		FyFriend friend;
		for(Long id:ids){
			friend=friendDao.findOne(id);
			friend.setGroup(group);
			this.update(friend);
			
		}
		
	}

}
