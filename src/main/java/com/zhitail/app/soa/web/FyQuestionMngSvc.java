package com.zhitail.app.soa.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.entity.FyQuestion;

import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FyQuestionMngSvc")
@RestController
public class FyQuestionMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyQuestionMng questionMng;
	@Autowired
	private FyUserMng userMng;
	@RequestMapping(value = "/findQuestion",method=RequestMethod.GET)
	public Result findQuestion(String token,Long id) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		FyQuestion question = questionMng.findById(id);
		
		return new Result(question);
	}
	@RequestMapping(value = "/queryQuestion",method=RequestMethod.GET)
	public Result queryQuestion(String token, Integer pageNo,Integer pageSize,FyQuestion search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		Pagination<FyQuestion> page = questionMng.getPage(pageNo,pageSize,search);
		
		return new Result(page);
	}
	
	@RequestMapping(value = "/addQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion( String token,FyQuestion question,Integer diff) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		
		if(question.getId()==null){
			question.setCreateTime(new Date());
			FyUser u=userMng.findByUserName(loginManager.getUser(token));
			question.setUserId(u.getId());
			question.setJson((new JSONArray()).toJSONString());
			question.setTagsJson((new JSONArray()).toJSONString());
			
			question = questionMng.save(question);
		}else{
			question= questionMng.update(question);
		}
		return new Result(question);
	
	}
	
	
	
	@RequestMapping(value = "/removeQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeQuestion(String token, Long[] ids) {

		
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		questionMng.delete(ids);

		return new Result(true);
	

	}
}