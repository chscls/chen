package com.zhitail.app.entity.middle;

public class FyTestRecordStatistics {
		private Long orgId;
		private Integer maxScore;
		public FyTestRecordStatistics (Long orgId,Integer maxScore){
			this.orgId=orgId;
			this.maxScore=maxScore;
		}
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}
		public Integer getMaxScore() {
			return maxScore;
		}
		public void setMaxScore(Integer maxScore) {
			this.maxScore = maxScore;
		}
}
