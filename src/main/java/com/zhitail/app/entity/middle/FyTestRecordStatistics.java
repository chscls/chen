package com.zhitail.app.entity.middle;

import java.util.Date;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTest.Mode;

public class FyTestRecordStatistics {
		private String code;
		private String title;
		private Long orgId;
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
		public FyTestRecordStatistics (Date updateTime,Long orgId,String code,String title,Long count,Double maxScore,Double minScore,Double avgScore,Mode mode){
			this.updateTime=updateTime;
			
			this.orgId=orgId;
			this.code=code;
			this.count=count;
			this.maxScore=maxScore;
			this.minScore=minScore;
			this.avgScore =avgScore;
			this.title=title;
			this.mode=mode;
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
