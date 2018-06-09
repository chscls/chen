package com.zhitail.app.soa.web;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

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
import com.zhitail.test.entity.User;

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
	private FyUserMng userMng;
	 @Resource
	    Configuration cfg;
	public Map<String, Object> getContent() throws IOException {

		// 从数据库中获取数据， 出于演示目的， 这里数据不从数据库获取， 而是直接写死
		
		Map<String, Object> variables = new HashMap<String, Object>(3);

		List<User> userList = new ArrayList<User>();

		User tom = new User("Tom", 19, 1);
		User amy = new User("Amy", 28, 0);
		User leo = new User("Leo", 23, 1);

		userList.add(tom);
		userList.add(amy);
		userList.add(leo);

		variables.put("title", "用户列表");
		variables.put("userList", userList);
		
		return variables;
	}
	public  String generate(String template)
			throws Exception {

		Template tp = cfg.getTemplate(template);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tp.setEncoding("UTF-8");
		tp.process(getContent(), writer);
		String htmlStr = stringWriter.toString();
		writer.flush();
		writer.close();
		return htmlStr;
	}
	@RequestMapping(value = "/ok",method=RequestMethod.GET)
	public Result ok() {
		
		try {
			String outputFile = "D:\\sample.pdf";
			Map<String, Object> variables = new HashMap<String, Object>(3);

			List<User> userList = new ArrayList<User>();

			User tom = new User("Tom", 19, 1);
			User amy = new User("Amy", 28, 0);
			User leo = new User("Leo", 23, 1);

			userList.add(tom);
			userList.add(amy);
			userList.add(leo);

			variables.put("title", "用户列表");
			variables.put("userList", userList);

			String htmlStr = generate("fileTemplate.html");

			OutputStream out = new FileOutputStream(outputFile);
			PdfGenerator.generatePlus(htmlStr, out);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new Result("xxxxxxxx");
	}
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
