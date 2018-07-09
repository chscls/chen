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
import com.zhitail.app.entity.FyShow;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.middle.AntUser;
import com.zhitail.app.manager.FyShowMng;
import com.zhitail.app.manager.FyShowMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.app.soa.PublicComponent;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;



@RequestMapping("/services/FyShowMngSvc")
@RestController
public class FyShowMngSvc {
	
	@Autowired
	private FyShowMng beanMng;
	 @Autowired
		private PublicComponent pc;
	@TokenAuth(value="token")
	@RequestMapping(value = "/query",method=RequestMethod.GET)
	public Result query(String token, Integer pageNo,Integer pageSize,FyShow search) {
		Pagination<FyShow> page = beanMng.getPage(pageNo,pageSize,search);
		
		return new Result(page);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result find( String token,Long id) {
		
		FyShow	bean = beanMng.findById(id);
		pc.fullShow(bean);
		return new Result(bean);
	
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result add( String token,FyShow bean) {
		if(bean.getId()==null){
		
			bean = beanMng.save(bean);
		}else{
			bean = beanMng.update(bean);
		}
		return new Result(bean);
	
	}
	
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result remove(String token, Long[] ids) {
		beanMng.delete(ids);
		return new Result(true);
	}
}
