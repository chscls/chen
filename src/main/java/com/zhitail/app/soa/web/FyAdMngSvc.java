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
import com.zhitail.app.entity.FyAd;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyAdMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyAdMngSvc")
@RestController
public class FyAdMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyAdMng beanMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/querybean",method=RequestMethod.GET)
	public Result querybean(String token, Integer pageNo,Integer pageSize,FyAd search) {
		Pagination<FyAd> page = beanMng.getPage(pageNo,pageSize,search);
		return new Result(page);
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/addbean", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addbean( String token,FyAd bean) {
		if(bean.getId()==null){
		
			bean = beanMng.save(bean);
		}else{
			bean = beanMng.update(bean);
		}
		return new Result(bean);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/removebean", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removebean(String token, Long[] ids) {
		beanMng.delete(ids);
		return new Result(true);
	}
}
