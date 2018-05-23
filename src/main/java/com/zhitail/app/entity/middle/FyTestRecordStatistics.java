package com.zhitail.app.entity.middle;

import com.zhitail.app.entity.FyTest;

public class FyTestRecordStatistics {
		private Long orgId;
		private Long count;
		private Double maxScore;
		private Double minScore;
		public Double getArgScore() {
			return argScore;
		}
		public void setArgScore(Double argScore) {
			this.argScore = argScore;
		}
		private Double argScore;
		public Long getCount() {
			return count;
		}
		public void setCount(Long count) {
			this.count = count;
		}
		private FyTest test;
		public FyTestRecordStatistics (Long orgId,Long count,Double maxScore,Double minScore,Double argScore){
			this.orgId=orgId;
			this.count=count;
			this.maxScore=maxScore;
			this.minScore=minScore;
			this.argScore =argScore;
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
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}
		public Double getMaxScore() {
			return maxScore;
		}
		public void setMaxScore(Double maxScore) {
			this.maxScore = maxScore;
		}
		
}
