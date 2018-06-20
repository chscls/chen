package com.zhitail.app.soa.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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

@RequestMapping("/services/FyTestRecordMngSvc")
@RestController
public class FyTestRecordMngSvc {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private FyTestRecordMng testRecordMng;
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyUserMng userMng;

	@TokenAuth(value = "token")
	@RequestMapping(value = "/findTestRecord", method = RequestMethod.GET)
	public Result findQuestion(String token, Long id) {
		FyTestRecord testRecord = testRecordMng.findById(id);

		return new Result(testRecord);
	}
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecordStatistics", method = RequestMethod.GET)
	public Result queryTestRecordStatistics(String token, String code) {
		String[] codes= new String[1];
		codes[0] =code;
	   List<FyTestRecordStatistics>  list =  testRecordMng.groupByCodes(codes);
		
		
		return new Result(list.get(0));
	}
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecordDetail", method = RequestMethod.GET)
	public Result queryTestRecordDetail(String token, Integer start, Integer count, FyTestRecord search,String userkey) {
		Long[] ids=null;
		if(StringUtils.isNotBlank(userkey)) {
		List<Object> objs = 	userMng.findIdsByName(userkey);
		ids=objs.toArray(new Long[objs.size()]);
		if(ids.length==0) {
			
			return new Result(new ArrayList<FyTestRecord>());
			
		}
		}
		
		List<FyTestRecord> list = testRecordMng.getDetail(start, count, search,ids);
		if(list.size()==0) {
			
			return new Result(list);
			
		}
		List<Long> userIds = new ArrayList<Long>(list.size());
		for (FyTestRecord r : list) {
			userIds.add(r.getUserId());
		}
		List<FyUser> users = userMng.findByIds(userIds.toArray(new Long[userIds.size()]));
		Map<Long, FyUser> map = new HashMap<Long, FyUser>();
		for (FyUser t : users) {
			map.put(t.getId(), t);
		}
		for (FyTestRecord s : list) {
			FyUser lite = map.get(s.getUserId());
			lite.setPassword(null);
			lite.setIsDel(null);
			lite.setOpenid(null);
			s.setUser(lite);
		}
		return new Result(list);

	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryMyTestRecord", method = RequestMethod.GET)
	public Result queryMyTestRecord(String token, Integer pageNo, Integer pageSize, FyTestRecord search) {
		
		
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		search.setUserId(u.getId());
		Pagination<FyTestRecord> page = testRecordMng.getMyPage(pageNo, pageSize, search);
		for(FyTestRecord r:page.getList()) {
			r.lite();
		}
		return new Result(page);
	}
	
	
	
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecord", method = RequestMethod.GET)
	public Result queryTestRecord(String token, Integer pageNo, Integer pageSize, FyTestRecord search,Long orgId) {
		Pagination<FyTestRecordStatistics> page2 = new Pagination<FyTestRecordStatistics>();
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		search.setUserId(u.getId());
		Pagination<String> page = testRecordMng.getPage(pageNo, pageSize, search, orgId);
		if (page == null) {
			page2.setList(new ArrayList());
			page2.setPageNo(pageNo);
			page2.setPageSize(pageSize);
			page2.setTotalCount(0);
			return new Result(page2);
		}

		List<FyTestRecordStatistics> list2 = testRecordMng
				.groupByCodes(page.getList().toArray(new String[page.getList().size()]));

		List<FyTest> list = testMng.findByCodes(page.getList().toArray(new String[page.getList().size()]));
		Map<String, FyTest> map = new HashMap<String, FyTest>();
		for (FyTest t : list) {
			map.put(t.getCode(), t);
		}
		for (FyTestRecordStatistics s : list2) {
			if(map.containsKey(s.getCode())) {
			FyTest lite = map.get(s.getCode());
			lite.setQuestions(null);

			s.setTest(lite);
			}
		}

		page2.setList(list2);
		page2.setPageNo(page.getPageNo());
		page2.setPageSize(page.getPageSize());
		page2.setTotalCount(page.getTotalCount());

		return new Result(page2);
	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/addTestRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result addTest(String token, FyTestRecord test) {

		test = testRecordMng.update(test);

		return new Result(test);

	}

	@RequestMapping(value = "/removeTestRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public Result removeTest(String token, Long[] ids) {

		if (!loginManager.verify(token)) {
			return new Result(HttpStatus.UNAUTHORIZED);
		}
		testRecordMng.delete(ids);

		return new Result(true);

	}
}
