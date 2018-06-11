package com.zhitail.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.Application;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhitail.app.entity.FyTest.Mode;
import com.zhitail.app.entity.middle.FyQuestionItem;

@Entity
@Table(name = "fy_test_record")
public class FyTestRecord {

	public enum Status{
		create,wait,complete
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private Mode mode;
	private Double score;
	private Double goal;
	@Column(unique=true)
	private Long uuid;
	
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}

	private Status status;
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}

	private Boolean isQuestionnaire;
	
	public Boolean getIsQuestionnaire() {
		return isQuestionnaire;
	}
	public void setIsQuestionnaire(Boolean isQuestionnaire) {
		this.isQuestionnaire = isQuestionnaire;
	}

	private Integer limitSecond;
	public Integer getLimitSecond() {
		return limitSecond;
	}
	public void setLimitSecond(Integer limitSecond) {
		this.limitSecond = limitSecond;
	}
	
	private String title;
	private Date createTime;
	private Long userId;
	private Long teaId;
	@Column(unique=true)
	private Long code;
	public Long getTeaId() {
		return teaId;
	}
	public void setTeaId(Long teaId) {
		this.teaId = teaId;
	}

	@JsonIgnore
	@Lob
	private String json;
	@Transient
	
	private FyUser user;
	public FyUser getUser() {
		return user;
	}
	public void setUser(FyUser user) {
		this.user = user;
	}
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
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void lite() {
		// TODO Auto-generated method stub
		
		this.setJson("[]");
		
		
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void reFreshUuid() {
		// TODO Auto-generated method stub
		this.uuid=Application.getSnowflakeIdWorker().nextId();
	}
}
