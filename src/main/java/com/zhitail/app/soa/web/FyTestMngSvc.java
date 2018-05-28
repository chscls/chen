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
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FyTestMngSvc")
@RestController
public class FyTestMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyUserMng userMng;
	@TokenAuth(value="token")
	@RequestMapping(value = "/findTest",method=RequestMethod.GET)
	public Result findQuestion(String token,Long id) {
		
		FyTest test= testMng.findById(id);
		
		return new Result(test);
	}
	@RequestMapping(value = "/queryTest",method=RequestMethod.GET)
	public Result queryTest(String token, Integer pageNo,Integer pageSize,FyTest search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyTest> page = testMng.getPage(u.getId(),pageNo,pageSize,search);
		
		return new Result(page);
	}
	
	@RequestMapping(value = "/addTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addTest( String token,FyTest test) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		if(test.getId()==null){
			test.setCreateTime(new Date());
			FyUser u=userMng.findByUserName(loginManager.getUser(token));
			test.setCode(UUID.randomUUID().toString().replace("-", ""));
			test.setUserId(u.getId());
			test = testMng.save(test);
		}else{
			test= testMng.update(test);
		}
		return new Result(test);
	
	}
	@RequestMapping(value = "/updateTestQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result updateTestQuestions(String token,Long id, Long[] qids) {
		
		FyTest t  = testMng.updateTestQuestions(id,qids);
		return new Result(t);

		
		
	}
	
	
	@RequestMapping(value = "/removeTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeTest(String token, Long[] ids) {

		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		testMng.delete(ids);

		return new Result(true);
	

	}
}
