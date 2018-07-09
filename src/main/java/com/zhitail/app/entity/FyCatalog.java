package com.zhitail.app.entity;



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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.zhitail.app.entity.middle.ShowAble;


@Entity
@Table(name = "fy_catalog")
public class FyCatalog implements ShowAble{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FyCatalog other = (FyCatalog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private Long parentId;
	private String templete;
	private String path;
	private String img;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTemplete() {
		return templete;
	}
	public void setTemplete(String templete) {
		this.templete = templete;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@OneToMany(orphanRemoval=true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@IndexColumn(name="priority")
	@JoinTable(name="fy_catalog_catalog",joinColumns={@JoinColumn(name="parent_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="child_id",referencedColumnName="id")}
	)
	private List<FyCatalog>  children;
	
	public List<FyCatalog> getChildren() {
		return children;
	}
	public void setChildren(List<FyCatalog> children) {
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
