package com.zhitail.app.soa.web;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
import com.zhitail.test.SnowflakeIdWorker;
import com.zhitail.test.generator.PdfGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
@RequestMapping("/services/FyTestMngSvc")
@RestController
public class FyTestMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyQuestionMng questionMng;
	@Autowired
	private FyUserMng userMng;
	@Autowired
	private FyTestRecordMng  testRecordMng;

	
	@TokenAuth(value="token")
	@RequestMapping(value = "/findTest",method=RequestMethod.GET)
	public Result findQuestion(String token,Long id) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyTest test= testMng.findById(id);
		testMng.fullQuestions(test);
		return new Result(test);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/queryTest",method=RequestMethod.POST)
	public Result queryTest(String token, Integer pageNo,Integer pageSize,String code,String title,String isQuestionnaire,String mode,String status) {
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyTest> page = testMng.getPage(u.getId(),pageNo,pageSize,code, title,isQuestionnaire, mode,status);
		for(FyTest ft:page.getList()) {
			ft.lite();		
		}
		return new Result(page);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/addTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addTest( String token,FyTest test) {	
		if(test.getId()==null){
			test.setCreateTime(new Date());
			FyUser u=userMng.findByUserName(loginManager.getUser(token));
			test.refreshCode();
			test.setUserId(u.getId());
			test = testMng.save(test);
		}else{
			test.refreshCode();
			test= testMng.update(test);
		}
		testMng.fullQuestions(test);
		return new Result(test);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/updateTestQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result updateTestQuestions(String token,Long id, Long[] qids) {
		FyTest t  = testMng.updateTestQuestions(id,qids);
		testMng.fullQuestions(t);
		return new Result(t);	
	}
	
	@TokenAuth(value="token")
	@RequestMapping(value = "/removeTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeTest(String token, Long[] ids) {
		testMng.delete(ids);
		return new Result(true);
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/recycleTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result recycleTest(String token, Long[] ids) {
		testMng.recycle(ids);
		return new Result(true);
	}
}
