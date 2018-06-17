package com.zhitail.app.entity.middle;

public class AntUser {
	private String name;
	private String avatar;
	private String userid;
	private Integer notifyCount;
	private Long questionCount;
	private Long questionCapacity;
	private Long recycleCount;
	public Long getRecycleCount() {
		return recycleCount;
	}
	public void setRecycleCount(Long recycleCount) {
		this.recycleCount = recycleCount;
	}
	public Long getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Long questionCount) {
		this.questionCount = questionCount;
	}
	public Long getQuestionCapacity() {
		return questionCapacity;
	}
	public void setQuestionCapacity(Long questionCapacity) {
		this.questionCapacity = questionCapacity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getNotifyCount() {
		return notifyCount;
	}
	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}
}
