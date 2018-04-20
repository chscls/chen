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










import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FySensitiveMngSvc")
@RestController
public class FySensitiveMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FySensitiveMng sensitiveMng;
	@RequestMapping(value = "/querySensitive",method=RequestMethod.GET)
	public Result querySensitive(String token, Integer pageNo,Integer pageSize,FySensitive search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		Pagination<FySensitive> page = sensitiveMng.getPage(pageNo,pageSize,search);
		
		return new Result(page);
	}
	
	/**
	 * 新增用户
	 * @param token
	 * @param user  用户
	 * @param groupId  分组id
	 * @return
	 */
	@RequestMapping(value = "/addSensitive", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addSensitive( String token,FySensitive sensitive) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		if(sensitive.getId()==null){
		
			sensitive = sensitiveMng.save(sensitive);
		}else{
			sensitive = sensitiveMng.update(sensitive);
		}
		return new Result(sensitive);
	
	}
	
	
	/**
	 *  删除用户
	 * @param token
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/removeSensitive", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeSensitive(String token, Long[] ids) {

		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		sensitiveMng.delete(ids);

		return new Result(true);
	

	}
}
