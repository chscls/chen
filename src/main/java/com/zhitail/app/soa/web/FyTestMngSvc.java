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
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

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
	
	private FyTest fullQuestions(FyTest test) {
		Long[] qids = new Long[test.getQuestionIds().size()];
		List<FyQuestion> qs=questionMng.findByIds(qids);
		Map<Long,FyQuestion> map=new HashMap<Long,FyQuestion>(qs.size());
		for(FyQuestion q:qs) {
			map.put(q.getId(), q);
		}
		List<FyQuestion> fqs = new ArrayList<FyQuestion>();
		for(Long id:qids) {
			if(map.containsKey(id)) {
			fqs.add(map.get(id));
			}
		}
		test.setQuestions(fqs);
		return test;
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/findTest",method=RequestMethod.GET)
	public Result findQuestion(String token,Long id) {
		
		FyTest test= testMng.findById(id);
		List<Long> ids = test.getQuestionIds();
		if(ids.size()>0) {
	
			fullQuestions(test);
		
		}else {
		test.setQuestions(new ArrayList<FyQuestion>());
		}
		return new Result(test);
	}
	@RequestMapping(value = "/queryTest",method=RequestMethod.GET)
	public Result queryTest(String token, Integer pageNo,Integer pageSize,FyTest search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u=userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyTest> page = testMng.getPage(u.getId(),pageNo,pageSize,search);
		for(FyTest ft:page.getList()) {
			ft.lite();
			
		}
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
			test.setCode(UUID.randomUUID().toString().replace("-", ""));
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
