package com.zhitail.app.soa.web;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;



@RequestMapping("/services/FyUserMngSvc")
@RestController
public class FyUserMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyUserMng userMng;
	
	
	@RequestMapping(value = "/currentUser",method=RequestMethod.GET)
	public ResponseEntity<AntUser> currentUser(String token) {
		
		
		AntUser au = new AntUser();
		FyUser u=userMng.findById(1L);
		au.setAvatar(u.getHeadImg());
		au.setName(u.getRealname());
		au.setNotifyCount(12);
		au.setUserid(u.getId().toString());
		return new  ResponseEntity<AntUser>(au,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/queryUser",method=RequestMethod.GET)
	public ResponseEntity<Pagination<FyUser>> queryUser(String token,FyUser.Type type, Integer pageNo,Integer pageSize,FyUser search) {
		
		Pagination<FyUser> page = userMng.getPage(type,pageNo,pageSize,search);
		
		return new  ResponseEntity<Pagination<FyUser>>(page,HttpStatus.OK);
	}
	
	/**
	 * 新增用户
	 * @param token
	 * @param user  用户
	 * @param groupId  分组id
	 * @return
	 */
	@RequestMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FyUser> addUser( String token,FyUser user,FyUser.Type type) {
		
		if(user.getId()==null){
		
		user = userMng.save(user);
		}else{
			user = userMng.update(user);
		}
		return new  ResponseEntity<FyUser>(user,HttpStatus.OK);
	}
	
	
	/**
	 *  删除用户
	 * @param token
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/removeUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> removeClazz(String token, Long[] ids) {

		

		userMng.delete(ids);

	
		return new  ResponseEntity<Boolean>(true,HttpStatus.OK);

	}
	
}
