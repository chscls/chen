package com.zhitail.app.soa.web;

import java.util.Date;

import org.springframework.beans.BeanUtils;
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
import com.zhitail.app.entity.FyQuestion.Status;
import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;
import com.zhitail.frame.common.annotion.TokenAuth;
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

	@TokenAuth(value = "token")
	@RequestMapping(value = "/findQuestion", method = RequestMethod.GET)
	public Result findQuestion(String token, Long id) {
		FyQuestion question = questionMng.findById(id);
		return new Result(question);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryQuestion", method = RequestMethod.POST)
	public Result queryQuestion(String token, String sorter, Long[] alreadyIds, Integer pageNo, Integer pageSize,
			String type, String difficulty, String status, String title, String tag) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		Pagination<FyQuestion> page = questionMng.getPage(sorter, alreadyIds, pageNo, pageSize, u.getId(), title, type,
				difficulty, status, tag);
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
		return new Result(page);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/updateOptions", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion(String token, Long id, String options, Boolean isQuestionnaire) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		FyQuestion q = questionMng.findById(id);
		Boolean isChange = false;
		if (q.getIsQuestionnaire() != isQuestionnaire) {
			isChange = true;
		}
		if (!q.getJson().equals(options)) {
			isChange = true;
		}
		if (q.getStatus() != Status.complete) {
			isChange = true;
		}

		q.setIsQuestionnaire(isQuestionnaire);
		q.setJson(options);
		q.setStatus(Status.complete);

		questionMng.update(q, isChange);
		return new Result(q);

	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/addQuestion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addQuestion(String token, FyQuestion question, String[] tags) {
		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		question.setTagsJson(tags != null ? JSONArray.toJSONString(tags) : null);
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
			FyQuestion q = questionMng.findById(question.getId());
			Boolean isChange = false;
			if (!q.getTitle().equals(question.getTitle())) {
				isChange = true;
			}
			q.setTitle(question.getTitle());
			if (!isChange&&!q.getTagsJson().equals(question.getTagsJson())) {
				isChange = true;
			}
			q.setTagsJson(question.getTagsJson());
			if (!isChange&&q.getIsRich()!=question.getIsRich()) {
				isChange = true;
			}
			q.setIsRich(question.getIsRich());
			if (!isChange&&q.getDifficulty()!=question.getDifficulty()) {
				isChange = true;
			}
			q.setDifficulty(question.getDifficulty());
			if(isChange) {
			question = questionMng.update(q,isChange);
			}else {
				return new Result(q);
			}
		}
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
