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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.Application;
import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.entity.middle.QuestionConfig;

@Entity
@Table(name = "fy_test")
public class FyTest {
	public enum Mode{
		free,singleLimit,totalLimit,race,
	}
	public enum Status{
		create,process,complete
	}
	public enum SaleStatus{
		create,apply,refuse,sale
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
	private Boolean isNoOrder=false;
	@ManyToOne
	@JoinColumn(name = "skin_id")
	private FySkin skin;
	private Double orgPrice;
	public Double getOrgPrice() {
		return orgPrice;
	}
	public void setOrgPrice(Double orgPrice) {
		this.orgPrice = orgPrice;
	}
	private Double price;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public FySkin getSkin() {
		return skin;
	}
	public void setSkin(FySkin skin) {
		this.skin = skin;
	}
	public Boolean getIsNoOrder() {
		return isNoOrder;
	}
	public void setIsNoOrder(Boolean isNoOrder) {
		this.isNoOrder = isNoOrder;
	}
	public Boolean getIsQuestionnaire() {
		return isQuestionnaire;
	}
	public void setIsQuestionnaire(Boolean isQuestionnaire) {
		this.isQuestionnaire = isQuestionnaire;
	}
	private Mode mode;
	public SaleStatus getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}
	private Boolean isRecycle=false;
	private SaleStatus saleStatus=SaleStatus.create;
	public Boolean getIsRecycle() {
		return isRecycle;
	}
	public void setIsRecycle(Boolean isRecycle) {
		this.isRecycle = isRecycle;
	}
	private Date recycleTime;
	public Date getRecycleTime() {
		return recycleTime;
	}
	public void setRecycleTime(Date recycleTime) {
		this.recycleTime = recycleTime;
	}
	@Transient
	private Double score;
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
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
	private Date updateTime;
	private Long userId;
	private Integer allowTime=1;
	
	public Integer getAllowTime() {
		return allowTime;
	}
	public void setAllowTime(Integer allowTime) {
		this.allowTime = allowTime;
	}
	@Column(unique=true,length=18)
	private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<QuestionConfig> getQuestionConfigs() {
		if(json!=null){
			
			return JSONArray.parseArray(json,QuestionConfig.class);
			}else{
				return new ArrayList<QuestionConfig>(0);
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
		this.setCount(this.getQuestionConfigs().size());
		double s=0.0;
		for(QuestionConfig qc:this.getQuestionConfigs()) {
			s+=qc.getScore()!=null?qc.getScore():0.0;
		}
		this.setScore(s);
		this.setJson(null);
		this.setQuestions(null);
		
		
		
	}
	public void refreshCode() {
		// TODO Auto-generated method stub
		this.code=Application.getSnowflakeIdWorker().nextId()+"";
		this.updateTime=new Date();
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
