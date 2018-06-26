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

import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyFriend;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyTest.Mode;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.manager.FyTestMng;
import com.zhitail.app.manager.FyFriendMng;
import com.zhitail.app.manager.FySensitiveMng;
import com.zhitail.app.manager.FyTestRecordMng;
import com.zhitail.app.manager.FyTestVersionMng;
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
	@Autowired
	private FyFriendMng friendMng;
	@Autowired
	private FyTestVersionMng versionMng;
	@TokenAuth(value = "token")
	@RequestMapping(value = "/makeScore", method = RequestMethod.POST)
	public Result makeScore(String token, Long id,String scores) {
		
		JSONObject jo = JSONObject.parseObject(scores);
		
		FyTestRecord testRecord = testRecordMng.makeScore(id,jo);
		
		return new Result(testRecord);
	}
	@TokenAuth(value = "token")
	@RequestMapping(value = "/findTestRecord", method = RequestMethod.GET)
	public Result findQuestion(String token, Long id) {
		FyTestRecord testRecord = testRecordMng.findById(id);

		return new Result(testRecord);
	}
	@TokenAuth(value = "token")
	@RequestMapping(value = "/getWait", method = RequestMethod.GET)
	public Result getWait(String token, String code) {
		FyTestRecord testRecord = testRecordMng.getWait(code);

		return new Result(testRecord);
	}
	
	
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecordStatistics", method = RequestMethod.GET)
	public Result queryTestRecordStatistics(String token, String code) {
		
		FyTestVersion fr = new FyTestVersion();
		fr.setCode(code);
	    Pagination<FyTestRecordStatistics>  page =  testRecordMng.groupByCodes(fr,1,10);
	    fr=versionMng.findByCode(code);
		if(page.getList().size()>0) {
			FyTestRecordStatistics fs=page.getList().get(0);
			fs.setVersion(fr);
		return new Result(fs);
		}else {
			FyTest ft = 	testMng.findByCode(code);
			
			if(ft!=null) {
				testMng.fullQuestions(ft);
				FyTestRecordStatistics fs=new FyTestRecordStatistics (ft);
				double score=0.0;
				for(QuestionConfig qc:ft.getQuestionConfigs()) {
					score+=qc.getScore();
				}
				double total=0.0;
				for(FyQuestion q:ft.getQuestions()) {
					total+=(q.getScore()*(100-q.getDifficulty()))/score;
				}
				
				
				double rate = total/100;
				fs.setRate(rate);
				return new Result(fs);
			}else {
				
				return new Result(new FyTestRecordStatistics (fr));		
			}
		}
	  
	   
	}
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecordDetail", method = RequestMethod.GET)
	public Result queryTestRecordDetail(String token, Integer pageNo, Integer pageSize, FyTestRecord search,FyTestVersion version,String userkey,String sort) {
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		Long[] ids=null;
		if(StringUtils.isNotBlank(userkey)) {
		List<Object> objs = 	userMng.findIdsByName(userkey);
		ids=objs.toArray(new Long[objs.size()]);
		if(ids.length==0) {
			Pagination<FyTestRecord> page  =new Pagination<FyTestRecord>();
			page.setList(new ArrayList<FyTestRecord>());
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setTotalCount(0);
			return new Result(page);
			
		}
		}
		
		Pagination<FyTestRecord> page = testRecordMng.getDetailPage(pageNo, pageSize, search,version,ids,sort);
		if(page.getList().size()==0) {
			return new Result(page);
		}
		List<Long> userIds = new ArrayList<Long>(page.getList().size());
		for (FyTestRecord r : page.getList()) {
			userIds.add(r.getUserId());
		}
		Long[] uids= userIds.toArray(new Long[userIds.size()]);
		List<FyUser> list = userMng.findByIds(uids);
		List<FyFriend> list2 = friendMng.findByIds(u.getId(),uids);
		Map<Long, FyFriend> map2 = new HashMap<Long, FyFriend>();
		Map<Long, FyUser> map = new HashMap<Long, FyUser>();
		for (FyUser t : list) {
			map.put(t.getId(), t);
		}
		for (FyFriend t : list2) {
			map2.put(t.getFriendId(), t);
		}
		for (FyTestRecord s : page.getList()) {
			FyUser lite = map.get(s.getUserId());
			lite.setPassword(null);
			lite.setIsDel(null);
			lite.setOpenid(null);
			s.setUser(lite);
			if(map2.containsKey(s.getUserId())) {
				s.setFriend(map2.get(s.getUserId()));
			}
		}
		return new Result(page);

	}

	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryMyTestRecord", method = RequestMethod.GET)
	public Result queryMyTestRecord(String token, Integer pageNo, Integer pageSize,FyTestVersion version, FyTestRecord search) {
		
		
		FyUser u = userMng.findByUserName(loginManager.getUser(token));
		search.setUserId(u.getId());
		Pagination<FyTestRecord> page = testRecordMng.getMyPage(pageNo, pageSize,version, search);
		for(FyTestRecord r:page.getList()) {
			r.lite();
		}
		return new Result(page);
	}
	
	
	
	@TokenAuth(value = "token")
	@RequestMapping(value = "/queryTestRecord", method = RequestMethod.GET)
	public Result queryTestRecord(String token, Integer pageNo, Integer pageSize, FyTestVersion search) {
		Pagination<FyTestRecordStatistics> page = testRecordMng
				.groupByCodes(search,pageNo,pageSize);
		return new Result(page);
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
