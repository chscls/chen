package com.zhitail.app.entity.middle;

import java.util.Date;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTest.Mode;
import com.zhitail.app.entity.FyTestVersion;

public class FyTestRecordStatistics {
		private String code;
		private String title;
		private Long orgId;
		private Double score;
		public Double getScore() {
			return score;
		}
		public void setScore(Double score) {
			this.score = score;
		}
		private Date updateTime;
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}
		private Mode mode;
		public Mode getMode() {
			return mode;
		}
		public void setMode(Mode mode) {
			this.mode = mode;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		private Long count;
		private Double maxScore;
		private Double minScore;
		public Double getAvgScore() {
			return avgScore;
		}
		public void setAvgScore(Double avgScore) {
			this.avgScore = avgScore;
		}
		private Double avgScore;
		public Long getCount() {
			return count;
		}
		public void setCount(Long count) {
			this.count = count;
		}
		private FyTest test;
		public FyTestRecordStatistics (Double score,Date updateTime,Long orgId,String code,String title,Long count,Double maxScore,Double minScore,Double avgScore,Mode mode){
			this.updateTime=updateTime;
			this.score = score;
			
			this.orgId=orgId;
			this.code=code;
			this.count=count;
			this.maxScore=maxScore;
			this.minScore=minScore;
			this.avgScore =avgScore;
			this.title=title;
			this.mode=mode;
		}
		public FyTestRecordStatistics() {
			// TODO Auto-generated constructor stub
		}
		public FyTestRecordStatistics(FyTestVersion fr) {
			this.updateTime=fr.getUpdateTime();
			this.score = fr.getScore();
			
			this.orgId=fr.getOrgId();
			this.code=fr.getCode();
			this.count=0L;
			this.maxScore=0.0;
			this.minScore=0.0;
			this.avgScore =0.0;
			this.title=fr.getTitle();
			this.mode=fr.getMode();
			
			// TODO Auto-generated constructor stub
		}
		public FyTestRecordStatistics(FyTest fr) {
			this.updateTime=fr.getUpdateTime();
			double s=0.0;
			for(QuestionConfig qc:fr.getQuestionConfigs()) {
				s+=qc.getScore()!=null?qc.getScore():0.0;
			}
			this.score = s;
			
			this.orgId=fr.getId();
			this.code=fr.getCode();
			this.count=0L;
			this.maxScore=0.0;
			this.minScore=0.0;
			this.avgScore =0.0;
			this.title=fr.getTitle();
			this.mode=fr.getMode();
			// TODO Auto-generated constructor stub
		}
		public Double getMinScore() {
			return minScore;
		}
		public void setMinScore(Double minScore) {
			this.minScore = minScore;
		}
		public FyTest getTest() {
			return test;
		}
		public void setTest(FyTest test) {
			this.test = test;
		}
	
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Double getMaxScore() {
			return maxScore;
		}
		public void setMaxScore(Double maxScore) {
			this.maxScore = maxScore;
		}
		
}
