package com.zhitail.app.entity;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fy_user")
public class FyUser {
	public enum Type{
		admin,user
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Boolean isDel=false; 
	private Type type;
	
	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}

	@Column(unique=true)
	private String mobile;
	
	@Column(unique=true)
	private String openid;
	
	@Column(unique=true)
	private String realname;
	
	@Column(unique=true)
	private String nickname;
	
	private String headImg;
	
	private String password;
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Long questionCount=0L;
	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private Long questionCapacity=50L;
	public Long getQuestionCapacity() {
		return questionCapacity;
	}

	public void setQuestionCapacity(Long questionCapacity) {
		this.questionCapacity = questionCapacity;
	}

	public Long getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Long questionCount) {
		this.questionCount = questionCount;
	}

	private Long score=0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
}
