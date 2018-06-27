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
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FySensitiveMngSvc")
@RestController
public class FySensitiveMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FySensitiveMng sensitiveMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/query",method=RequestMethod.GET)
	public Result query(String token, Integer pageNo,Integer pageSize,FySensitive search) {
		Pagination<FySensitive> page = sensitiveMng.getPage(pageNo,pageSize,search);
		return new Result(page);
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result add( String token,FySensitive sensitive) {
		if(sensitive.getId()==null){
		
			sensitive = sensitiveMng.save(sensitive);
		}else{
			sensitive = sensitiveMng.update(sensitive);
		}
		return new Result(sensitive);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result remove(String token, Long[] ids) {
		sensitiveMng.delete(ids);
		return new Result(true);
	}
}
