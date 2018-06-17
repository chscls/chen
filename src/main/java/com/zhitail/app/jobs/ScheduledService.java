package com.zhitail.app.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhitail.app.manager.FyQuestionMng;
import com.zhitail.app.manager.FyTestMng;

@Component
public class ScheduledService {
	
	@Autowired
	private FyTestMng testMng;
	@Autowired
	private FyQuestionMng questionMng;
    @Scheduled(cron = "0 55 10 * * ?")
    public void clearQuestion(){
    	testMng.clear();
    	questionMng.clear();
        System.out.println(System.currentTimeMillis());
    }
  
}