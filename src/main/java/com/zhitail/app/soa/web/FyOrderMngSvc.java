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
import com.zhitail.app.entity.FyOrder;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyOrderMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyOrderMngSvc")
@RestController
public class FyOrderMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyOrderMng orderMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/queryOrder",method=RequestMethod.GET)
	public Result querySensitive(String token, Integer pageNo,Integer pageSize,FyOrder search) {
		Pagination<FyOrder> page = orderMng.getPage(pageNo,pageSize,search);
		return new Result(page);
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/addOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addSensitive( String token,FyOrder sensitive) {
		if(sensitive.getId()==null){
		
			sensitive = orderMng.save(sensitive);
		}else{
			sensitive = orderMng.update(sensitive);
		}
		return new Result(sensitive);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/removeOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeSensitive(String token, Long[] ids) {
		orderMng.delete(ids);
		return new Result(true);
	}


}
