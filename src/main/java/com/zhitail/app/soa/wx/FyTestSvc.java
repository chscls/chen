package com.zhitail.app.soa.wx;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FyTestSvc")
@RestController
public class FyTestSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyTestRecordMng testRecordMng;
	@Autowired
	private FyUserMng userMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/findTest",method=RequestMethod.GET)
	public Result findTest(String token,String code) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyTest test= testMng.findByCode(code);
		if(test==null) {
			return Result.error("该试卷不存在");
		}
		//test.lite();
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		FyTestRecord tr=new FyTestRecord();
		tr.setCode(test.getCode());
		tr.setUserId(u.getId());
		List<FyTestRecord> records= testRecordMng.getList( 0, 5,tr);
		for(FyTestRecord ftr:records) {
			ftr.lite();
		}
		
		Integer count= testRecordMng.getTotal(tr);
		JSONObject jo = new JSONObject();
		jo.put("test", test);
		jo.put("records", records);
		jo.put("isNew",records.size()==0 );
		jo.put("isAllow",test.getAllowTime()>=count );
		jo.put("isMore", count>5 );
		jo.put("total", count );
		return new Result(jo);
	}
	@RequestMapping(value = "/queryTest",method=RequestMethod.GET)
	public Result queryTest(String token, Integer start,Integer count,FyTest search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		if(search.getIsSale()==null) {
			search.setUserId(u.getId());
		}
		
		List<FyTest> list = testMng.getList(start,count,search);
		for(FyTest t:	list) {
			t.lite();
		}
		return new Result(list);
	}
	
	
}
