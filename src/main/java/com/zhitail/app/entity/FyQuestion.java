package com.zhitail.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
	@Transient
	private Double score;
	@Transient
	private Double goal;
	private Boolean isRecycle=false;
	public Boolean getIsRecycle() {
		return isRecycle;
	}
	public void setIsRecycle(Boolean isRecycle) {
		this.isRecycle = isRecycle;
	}
	private Boolean isGrade =  false;
	private Date recycleTime;
	public Date getRecycleTime() {
		return recycleTime;
	}
	public void setRecycleTime(Date recycleTime) {
		this.recycleTime = recycleTime;
	}
	public Boolean getIsGrade() {
		return isGrade;
	}
	public void setIsGrade(Boolean isGrade) {
		this.isGrade = isGrade;
	}
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}
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
	public Boolean getIsQuestionnaire() {
		return isQuestionnaire;
	}
	public void setIsQuestionnaire(Boolean isQuestionnaire) {
		this.isQuestionnaire = isQuestionnaire;
	}
	private Boolean isRich=false;
	private Boolean isQuestionnaire=false;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isGrade == null) ? 0 : isGrade.hashCode());
		result = prime * result + ((isQuestionnaire == null) ? 0 : isQuestionnaire.hashCode());
		result = prime * result + ((isRecycle == null) ? 0 : isRecycle.hashCode());
		result = prime * result + ((isRich == null) ? 0 : isRich.hashCode());
		result = prime * result + ((json == null) ? 0 : json.hashCode());
		result = prime * result + ((recycleTime == null) ? 0 : recycleTime.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tagsJson == null) ? 0 : tagsJson.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FyQuestion other = (FyQuestion) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (difficulty == null) {
			if (other.difficulty != null)
				return false;
		} else if (!difficulty.equals(other.difficulty))
			return false;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isGrade == null) {
			if (other.isGrade != null)
				return false;
		} else if (!isGrade.equals(other.isGrade))
			return false;
		if (isQuestionnaire == null) {
			if (other.isQuestionnaire != null)
				return false;
		} else if (!isQuestionnaire.equals(other.isQuestionnaire))
			return false;
		if (isRecycle == null) {
			if (other.isRecycle != null)
				return false;
		} else if (!isRecycle.equals(other.isRecycle))
			return false;
		if (isRich == null) {
			if (other.isRich != null)
				return false;
		} else if (!isRich.equals(other.isRich))
			return false;
		if (json == null) {
			if (other.json != null)
				return false;
		} else if (!json.equals(other.json))
			return false;
		if (recycleTime == null) {
			if (other.recycleTime != null)
				return false;
		} else if (!recycleTime.equals(other.recycleTime))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (status != other.status)
			return false;
		if (tagsJson == null) {
			if (other.tagsJson != null)
				return false;
		} else if (!tagsJson.equals(other.tagsJson))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
}
