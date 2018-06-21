package com.zhitail.app.soa.web;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;










import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyGroup;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;

import com.zhitail.app.manager.FyGroupMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyGroupMngSvc")
@RestController
public class FyGroupMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyGroupMng groupMng;
	
	@Autowired
	private FyUserMng  userMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/queryGroup",method=RequestMethod.GET)
	public Result queryGroup(String token, Integer pageNo,Integer pageSize,FyGroup search) {
		Pagination<FyGroup> page = groupMng.getPage(pageNo,pageSize,search);
		return new Result(page);
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/addGroup", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addGroup( String token,FyGroup group) {
		if(group.getId()==null){
			FyUser u=userMng.findByUserName(loginManager.getUser(token));
			group.setUserId(u.getId());
			group = groupMng.save(group);
		}else{
			
			group = groupMng.update(group);
		}
		return new Result(group);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/removeGroup", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeGroup(String token, Long[] ids) {
		groupMng.delete(ids);
		return new Result(true);
	}
}
