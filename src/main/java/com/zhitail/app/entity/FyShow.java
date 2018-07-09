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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.alibaba.fastjson.JSONArray;
import com.zhitail.app.entity.middle.QuestionConfig;
import com.zhitail.app.entity.middle.ShowAble;

@Entity
@Table(name = "fy_show")
public class FyShow {
	public enum Type {
		catalog, brand
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private Long parentId;
	private Type type;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Transient
	private List<ShowAble> list;
	@Lob
	private String json;

	public List<Long> getShowIds() {
		if (json != null) {

			return JSONArray.parseArray(json, Long.class);
		} else {
			return new ArrayList<Long>(0);
		}
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public List<ShowAble> getList() {
		return list;
	}

	public void setList(List<ShowAble> list) {
		this.list = list;
	}

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@IndexColumn(name = "priority")
	@JoinTable(name = "fy_show_show", joinColumns = {
			@JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "child_id", referencedColumnName = "id") })
	private List<FyShow> children;

	public List<FyShow> getChildren() {
		return children;
	}

	public void setChildren(List<FyShow> children) {
		this.children = children;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
