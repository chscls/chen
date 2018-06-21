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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.Application;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhitail.app.entity.FyQuestion.Type;
import com.zhitail.app.entity.FyTest.Mode;
import com.zhitail.app.entity.middle.FyAnswer;
import com.zhitail.app.entity.middle.FyQuestionItem;

@Entity
@Table(name = "fy_test_record")
public class FyTestRecord {

	public enum Status{
		create,check,complete
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	
	
	private Double goal;
	@Column(unique=true,length=18)
	private String uuid;
	@ManyToOne
	@JoinColumn(name = "version_id")
	private FyTestVersion version;
	public FyTestVersion getVersion() {
		return version;
	}
	public void setVersion(FyTestVersion version) {
		this.version = version;
	}
	
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}

	private Status status;
	


	
	
	
	private Date createTime;
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	private Long userId;
	
	private String sign;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	

	@JsonIgnore
	@Lob
	private String json;
	@Transient
	
	private FyUser user;
	@Transient
	private FyFriend friend;

	public FyFriend getFriend() {
		return friend;
	}
	public void setFriend(FyFriend friend) {
		this.friend = friend;
	}
	public FyUser getUser() {
		return user;
	}
	public void setUser(FyUser user) {
		this.user = user;
	}
	@Transient
	public List<FyAnswer> getAnswers() {
		if(json!=null){
			return JSONArray.parseArray(this.json,FyAnswer.class);
			}else{
				return new ArrayList<FyAnswer>(0);
			}
	}
	@Transient
	public List<FyQuestion> questions;
	public List<FyQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<FyQuestion> questions) {
		this.questions = questions;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
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
		this.uuid=Application.getSnowflakeIdWorker().nextId()+"";
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void full() {
		if(this.getAnswers().size()==0) {
			this.questions = this.version.getQuestions();
		}else {
			List<FyQuestion> qs = this.version.getQuestions();
			List<FyAnswer> ans = this.getAnswers();
			FyQuestion q;
			FyAnswer a;
			for (int i = 0; i < qs.size(); i++) {
				a=ans.get(i);
				if(a==null) {
					continue;
				}
				q = qs.get(i);
				List<FyQuestionItem> items=q.getItems();
				if (q.getType() == Type.single || q.getType() == Type.judge||q.getType() == Type.mutiply) {
					
					if(a.getIndexs()==null||a.getIndexs().length==0) {
						continue;
					}
					for(int j=0;j<a.getIndexs().length;j++) {
						items.get(a.getIndexs()[j]).setIsAnswer(true);
					}
					
				} else {
					if(a.getAnswers()==null||a.getAnswers().length==0) {
						continue;
					}
					for(int j=0;j<a.getAnswers().length;j++) {
						items.get(j).setAnswer(a.getAnswers()[j]);
					}
				}
				q.setJson(JSONArray.toJSONString(items));
			}
			this.questions = qs;

		}
		// TODO Auto-generated method stub
		
	}
	
}
