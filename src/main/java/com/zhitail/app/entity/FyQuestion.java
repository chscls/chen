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
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.entity.middle.SubQuestionConfig;

@Entity
@Table(name = "fy_question")
public class FyQuestion {
	public String getSubQuestionJson() {
		return subQuestionJson;
	}

	public void setSubQuestionJson(String subQuestionJson) {
		this.subQuestionJson = subQuestionJson;
	}

	public enum Type {
		single, mutiply, judge, fill, ask, synthesis
	}

	public enum Status {
		create, check, complete
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String title;
	@Lob
	private String analysis;
	private Boolean hasImg = false;

	public Boolean getHasImg() {
		return hasImg;
	}

	public void setHasImg(Boolean hasImg) {
		this.hasImg = hasImg;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	private Integer difficulty = 0;
	@Transient
	private Double score;
	@Transient
	private Double rate;

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Transient
	private Double goal;
	private Boolean isRecycle = false;

	public Boolean getIsRecycle() {
		return isRecycle;
	}

	public void setIsRecycle(Boolean isRecycle) {
		this.isRecycle = isRecycle;
	}

	private Boolean isGrade = false;
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
	public List<String> getTags() {
		if (tagsJson != null) {
			return JSONArray.parseArray(this.tagsJson, String.class);
		} else {
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

	private Boolean isAnalysisRich = false;

	public Boolean getIsAnalysisRich() {
		return isAnalysisRich;
	}

	public void setIsAnalysisRich(Boolean isAnalysisRich) {
		this.isAnalysisRich = isAnalysisRich;
	}

	private Boolean isRich = false;
	private Boolean isQuestionnaire = false;

	public Boolean getIsRich() {
		return isRich;
	}

	public void setIsRich(Boolean isRich) {
		this.isRich = isRich;
	}

	private Status status = Status.create;

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
	public List<FyQuestionItem> getItems() {
		if (json != null) {
			return JSONArray.parseArray(this.json, FyQuestionItem.class);
		} else {
			return new ArrayList<FyQuestionItem>(0);
		}
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	@JsonIgnore
	@Lob
	private String subQuestionJson;
	@Transient
	private List<FyQuestion> subQuestions;

	public List<FyQuestion> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<FyQuestion> subQuestions) {
		this.subQuestions = subQuestions;
	}

	public List<SubQuestionConfig> getSubQuestionConfigs() {
		if (subQuestionJson != null) {

			return JSONArray.parseArray(subQuestionJson, SubQuestionConfig.class);
		} else {
			return new ArrayList<SubQuestionConfig>(0);
		}
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public void fullImg(String imgServer) {
		// TODO Auto-generated method stub
		if (this.getIsRich() != null && this.getIsRich()) {
			this.title = this.title.replace("<img src=\"", "<img src=\"" + imgServer);
		}
		if (this.getIsAnalysisRich() != null && this.getIsAnalysisRich()) {
			this.analysis = this.analysis.replaceAll("<img src=\"", "<img src=\"" + imgServer);
		}
		if (this.type == Type.synthesis) {
			if (this.getSubQuestions() != null) {
				for (FyQuestion q : this.getSubQuestions()) {
					q.fullImg(imgServer);
				}
			}
		} else {
			List<FyQuestionItem> items = this.getItems();
			for (FyQuestionItem item : items) {
				if (item.getIsRich() != null && item.getIsRich()) {
					item.fullImg(imgServer);
				}
			}
			this.json = JSONArray.toJSONString(items);
		}

	}

}
