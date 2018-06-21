package com.zhitail.app.soa.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;










import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyFriend;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyFriendMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyFriendMngSvc")
@RestController
public class FyFriendMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyFriendMng friendMng;
	@Autowired
	private FyUserMng userMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/confirmSign",method=RequestMethod.POST)
	public Result confirmSign(String token,Long recordId,Long userId,String realname) {
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		
		FyFriend ff = 	friendMng.check( u.getId(),userId);
		if(ff!=null) {
			ff.setRealname(realname);
			ff=friendMng.update(ff);
		}else {
			ff=new FyFriend();
			ff.setRealname(realname);
			ff.setUserId(u.getId());
			ff.setFriendId(userId);
			ff.setCreateTime(new Date());
			ff=friendMng.save(ff);
		}
	
		
		return new Result(ff);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/queryFriend",method=RequestMethod.GET)
	public Result queryFriend(String token, Integer pageNo,Integer pageSize,FyFriend search) {
		Pagination<FyFriend> page = friendMng.getPage(pageNo,pageSize,search);
		if(page.getList().size()==0) {
			return new Result(page);
		}
		List<Long> userIds = new ArrayList<Long>(page.getList().size());
		for (FyFriend r : page.getList()) {
			userIds.add(r.getUserId());
		}
		Long[] uids= userIds.toArray(new Long[userIds.size()]);
		List<FyUser> list = userMng.findByIds(uids);
		
		
		Map<Long, FyUser> map = new HashMap<Long, FyUser>();
		for (FyUser t : list) {
			map.put(t.getId(), t);
		}
		
		for (FyFriend s : page.getList()) {
			FyUser lite = map.get(s.getUserId());
			lite.setPassword(null);
			lite.setIsDel(null);
			lite.setOpenid(null);
			s.setUser(lite);
		
		}
		return new Result(page);
		
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/addFriend", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addFriend( String token,FyFriend friend) {
		if(friend.getId()==null){
		
			friend = friendMng.save(friend);
		}else{
			friend = friendMng.update(friend);
		}
		return new Result(friend);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/removeFriend", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeFriend(String token, Long[] ids) {
		friendMng.delete(ids);
		return new Result(true);
	}
}
