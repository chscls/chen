package com.zhitail.app.soa.wx;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FyTestRecordSvc")
@RestController
public class FyTestRecordSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestRecordMng testRecordMng;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyUserMng userMng;
	
	
	@RequestMapping(value = "/submit",method=RequestMethod.POST)
	public Result submit(String token,Long id,String answers) {
		
		
		FyTestRecord r = testRecordMng.submit(id,answers);
		
		
		return new Result(r );
		
		
	}
	@RequestMapping(value = "/queryTestRecord",method=RequestMethod.GET)
	public Result queryTestRecord(String token,Integer start,Integer count,FyTestRecord search) {
		
		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		search.setUserId(u.getId());
		List<FyTestRecord> list = testRecordMng.getList(start,count,search);
		for(FyTestRecord r:list) {
			r.lite();
		}
	
		return new Result( list );
		
		
		
	}
	@RequestMapping(value = "/addTestRecord",method=RequestMethod.GET)
	public Result addTestRecord(String token,String code,Long recordId) {
		
		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		FyTestRecord r = testRecordMng.addTestRecord(u.getId(),code,recordId);
		
	
		return new Result(r );
		
		
		
	}
}
