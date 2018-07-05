package com.zhitail.app.soa.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyQuestion.Status;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.middle.SubQuestionConfig;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.app.soa.PublicComponent;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

@RequestMapping("/services/FyQuestionMngSvc")
@RestController
public class FyQuestionMngSvc {
	 @Value("${com.zhitail.upload.imgServer}")
	 private String imgServer;
	 @Autowired
		private PublicComponent pc;
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyQuestionMng questionMng;
	@Autowired
	private FyUserMng userMng;
	
	
	@TokenAuth(value = "token")
	@RequestMapping(value = "/updateQuestionQuestions", method = RequestMethod.POST)
	public Result updateQuestionQuestions(String token, Long id,Long[] qids,Double rate) {
		
		Set<Long> s = new HashSet<Long>(qids.length);
		for(Long qid:qids) {
			s.add(qid);
		}
	
		FyQuestion question = questionMng.updateQuestionQuestions(id, s,rate);
		question.fullImg(imgServer);
		pc.fullQuestions(question);
		return new Result(question);
	}
	
	@TokenAuth(value = "token")
	@RequestMapping(value = "/findQuestion", method = RequestMethod.GET)
	public Result findQuestion(String token, Long id) {
		FyQuestion question = questionMng.findById(id);
		pc.fullQuestions(question);
		question.fullImg(imgServer);
		
		return new Result(question);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryQuestion", method = RequestMethod.POST)
	public Result queryQuestion(String token, String sorter, Long[] alreadyIds, Integer pageNo, Integer pageSize,
			String type, String difficulty, String status, String title, String tag) {
		
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyQuestion> page = questionMng.getPage(sorter, alreadyIds, pageNo, pageSize, u.getId(), title, type,
				difficulty, status, tag);
		for(FyQuestion q:page.getList()) {
			
			pc.fullQuestions(q);
			q.fullImg(imgServer);
			
			
		}
		return new Result(page);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryQuestionRe", method = RequestMethod.POST)
	public Result queryQuestionRe(String token, String sorter, Integer pageNo, Integer pageSize, String title,
			String tag) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyQuestion> page = questionMng.getRecyclePage(sorter, pageNo, pageSize, u.getId(), title, tag);
		for(FyQuestion q:page.getList()) {
			pc.fullQuestions(q);
			q.fullImg(imgServer);
			
		}
		return new Result(page);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/updateOptions", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion(String token, Long id, String options, Boolean isQuestionnaire) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		FyQuestion q = questionMng.findById(id);
		q.setId(id);
		q.setIsQuestionnaire(isQuestionnaire);
		q.setJson(options);
		q.setHasImg(options.contains("<img") || options.contains("<IMG"));
		q.setStatus(Status.complete);
		q = questionMng.update(q, true);
		q.fullImg(imgServer);
		pc.fullQuestions(q);
		return new Result(q);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/addQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion(String token, FyQuestion question, String[] tags) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		question.setTagsJson(tags != null ? JSONArray.toJSONString(tags) : null);
		if (question.getIsRich() && question.getIsAnalysisRich()) {
			question.setHasImg(false);
		} else {
			question.setHasImg(question.getTitle().contains("<img src=\"/u") || question.getAnalysis().contains("<img src=\"/u"));
		}
		if (question.getId() == null) {
			FyUser u = userMng.findByUserName(loginManager.getUser(token));
			Long count = questionMng.getCount(u.getId(), null);
			if (count >= u.getQuestionCapacity()) {
				return Result.error("超过额度,请清理你的题目以释放额度或者购买额度");
			}
			question.setCreateTime(new Date());

			question.setUserId(u.getId());
			question.setJson((new JSONArray()).toJSONString());
			question = questionMng.save(question, u);
		} else {
			question = questionMng.update(question, true);
		}
		
		question.fullImg(imgServer);
		pc.fullQuestions(question);
		return new Result(question);

	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/removeQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeQuestion(String token, Long[] ids) {
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		questionMng.delete(ids, u);
		return new Result(true);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/recycleQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result recycleQuestion(String token, Long[] ids) {
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		questionMng.recycle(ids, u);
		return new Result(true);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/recoveryQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result recoveryQuestion(String token, Long[] ids) {
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		questionMng.recovery(ids, u);
		return new Result(true);
	}
}
