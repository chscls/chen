package com.zhitail.app.soa.web;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FyTestRecordMngSvc")
@RestController
public class FyRecordMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestRecordMng testRecordMng;
	@Autowired
	private FyUserMng userMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/findTestRecord",method=RequestMethod.GET)
	public Result findQuestion(String token,Long id) {
		
		FyTestRecord testRecord= testRecordMng.findById(id);
		
		return new Result(testRecord);
	}
	@RequestMapping(value = "/queryTestRecord",method=RequestMethod.GET)
	public Result queryTestRecord(String token, Integer pageNo,Integer pageSize,FyTestRecord search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		Pagination<FyTestRecord> page = testRecordMng.getPage(pageNo,pageSize,search);
		
		return new Result(page);
	}
	
	@RequestMapping(value = "/addTestRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addTest( String token,FyTestRecord test) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
	
			test= testRecordMng.update(test);
		
		return new Result(test);
	
	}
	
	
	
	@RequestMapping(value = "/removeTestRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeTest(String token, Long[] ids) {

		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		testRecordMng.delete(ids);

		return new Result(true);
	

	}
}
