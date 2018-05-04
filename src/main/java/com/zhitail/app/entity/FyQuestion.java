package com.zhitail.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhitail.app.entity.middle.FyQuestionItem;
@Entity
@Table(name = "fy_question")
public class FyQuestion {
	public enum Type{
		single,mutiply,judge,fill,ask
	}
	public enum Status{
		create,check,complete
	}
	
	 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String title;
	private Integer difficulty=0;
	private Double score;
	@JsonIgnore
	private String tagsJson;
	@Transient
	public List<String> getTags(){
		if(tagsJson!=null){
			return JSONArray.parseArray(this.tagsJson,String.class);
			}else{
				return new ArrayList<String>(0);
			}
		
	}
	public String getTagsJson() {
		return tagsJson;
	}
	public void setTagsJson(String tagsJson) {
		this.tagsJson = tagsJson;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	private Type type;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	private Date createTime;
	private Boolean isRich=false;
	public Boolean getIsRich() {
		return isRich;
	}
	public void setIsRich(Boolean isRich) {
		this.isRich = isRich;
	}
	private Status status=Status.create;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	/**
	 * 标题
	 */
	private Long userId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 标题
	 */
	@JsonIgnore
	@Lob
	private String json;
	@Transient
	public List<FyQuestionItem> getItems(){
		if(json!=null){
			return JSONArray.parseArray(this.json,FyQuestionItem.class);
			}else{
				return new ArrayList<FyQuestionItem>(0);
			}
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	
}
