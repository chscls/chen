package com.zhitail.app.entity.middle;

public class QuestionUser {
	private Long qid;
	private Long uid;
	public QuestionUser() {
		
	}
public QuestionUser(Long qid,Long uid) {
		this.qid=qid;
		this.uid=uid;
	}
	public Long getQid() {
		return qid;
	}
	public void setQid(Long qid) {
		this.qid = qid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
