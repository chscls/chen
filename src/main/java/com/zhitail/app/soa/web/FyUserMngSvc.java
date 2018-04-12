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
import org.springframework.web.bind.annotation.RestController;

import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;



@RequestMapping("/soa/services/KfUserMngSvc")
@RestController
public class FyUserMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyUserMng userMng;
	
	
	@RequestMapping(value = "/queryUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pagination<FyUser>> queryUser(FyUser.Type type, String token,Long groupId,Integer pageNo,Integer pageSize,FyUser search) {
		
		Pagination<FyUser> page = userMng.getPage(type,pageNo,pageSize,search);
		
		return new  ResponseEntity<Pagination<FyUser>>(page,HttpStatus.OK);
	}
	
}
