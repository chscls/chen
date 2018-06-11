package com.zhitail.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

@Entity
@Table(name = "fy_test")
public class FyTest {
	public enum Mode{
		free,singleLimit,totalLimit,race,
	}
	public enum Status{
		create,process,complete
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Transient
	private Integer count=-1;
	public Integer getCount() {
		
		return count>0?count:this.questions!=null?this.questions.size():0;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	private Boolean isQuestionnaire=false;
	public Boolean getIsQuestionnaire() {
		return isQuestionnaire;
	}
	public void setIsQuestionnaire(Boolean isQuestionnaire) {
		this.isQuestionnaire = isQuestionnaire;
	}
	private Mode mode;
	private Status status=Status.create;
	
	private Integer limitSecond;
	public Integer getLimitSecond() {
		return limitSecond;
	}
	public void setLimitSecond(Integer limitSecond) {
		this.limitSecond = limitSecond;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	private String title;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private Date createTime;
	private Long userId;
	private Integer allowTime=1;
	private Boolean isSale;
	public Boolean getIsSale() {
		return isSale;
	}
	public void setIsSale(Boolean isSale) {
		this.isSale = isSale;
	}
	public Integer getAllowTime() {
		return allowTime;
	}
	public void setAllowTime(Integer allowTime) {
		this.allowTime = allowTime;
	}
	@Column(unique=true)
	private Long code;
	
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

	public List<Long> getQuestionIds() {
		if(json!=null){
			String x = "["+this.json.substring(0, json.length()-1)+"]";
			return JSONArray.parseArray(x,Long.class);
			}else{
				return new ArrayList<Long>(0);
			}
	}
	@Lob
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	@Transient
	private List<FyQuestion> questions;
	public List<FyQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<FyQuestion> questions) {
		this.questions = questions;
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
		this.setCount(this.getQuestionIds().size());
		this.setJson(null);
		this.setQuestions(null);
		
		
		
	}
	public void refreshCode() {
		// TODO Auto-generated method stub
		this.code=Application.getSnowflakeIdWorker().nextId();
	}
}
