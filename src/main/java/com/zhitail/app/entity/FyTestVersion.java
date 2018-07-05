package com.zhitail.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhitail.app.entity.FyTest.Mode;

@Entity
@Table(name = "fy_test_version")
public class FyTestVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Mode mode;
	private Double score;
	private Boolean isQuestionnaire;
	@Column(unique=true,length=18)
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne
	@JoinColumn(name = "skin_id")
	private FySkin skin;
	private Integer limitSecond;
	private String title;
	private Long teaId;
	private Long orgId;
	private Double rate;
	
	public FySkin getSkin() {
		return skin;
	}
	public void setSkin(FySkin skin) {
		this.skin = skin;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@JsonIgnore
	@Lob
	private String json;

	@Transient
	public List<FyQuestion> getQuestions() {
		if(json!=null){
			return JSONArray.parseArray(this.json,FyQuestion.class);
			}else{
				return new ArrayList<FyQuestion>(0);
			}
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	private Date updateTime;
	private Date createTime;
	private Boolean isNoOrder;
	public Boolean getIsNoOrder() {
		return isNoOrder;
	}
	public void setIsNoOrder(Boolean isNoOrder) {
		this.isNoOrder = isNoOrder;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Boolean getIsQuestionnaire() {
		return isQuestionnaire;
	}
	public void setIsQuestionnaire(Boolean isQuestionnaire) {
		this.isQuestionnaire = isQuestionnaire;
	}
	public Integer getLimitSecond() {
		return limitSecond;
	}
	public void setLimitSecond(Integer limitSecond) {
		this.limitSecond = limitSecond;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTeaId() {
		return teaId;
	}
	public void setTeaId(Long teaId) {
		this.teaId = teaId;
	}
	
}
