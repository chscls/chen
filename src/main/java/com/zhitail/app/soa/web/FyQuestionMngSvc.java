package com.zhitail.app.soa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;
@RequestMapping("/services/FySensitiveMngSvc")
@RestController
public class FyQuestionMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyQuestionMng questionMng;
	@RequestMapping(value = "/queryQuestion",method=RequestMethod.GET)
	public Result queryQuestion(String token, Integer pageNo,Integer pageSize,FyQuestion search) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		Pagination<FyQuestion> page = questionMng.getPage(pageNo,pageSize,search);
		
		return new Result(page);
	}
	
	@RequestMapping(value = "/addQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion( String token,FyQuestion question) {
		if(!loginManager.verify(token)){
			return  new Result(HttpStatus.UNAUTHORIZED);
		}
		if(question.getId()==null){
		
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
