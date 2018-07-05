package com.zhitail.app.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.Application;

@Entity
@Table(name = "fy_skin")
public class FySkin {
	public enum Status {
		create, sale, noSale
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String code;
	private Date createTime;
	private Double orgPrice;
	public Double getOrgPrice() {
		return orgPrice;
	}
	public void setOrgPrice(Double orgPrice) {
		this.orgPrice = orgPrice;
	}
	private Double price;
	private String img;
	@Lob
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	private Status status=Status.create;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void init() {
		this.createTime=new Date();
		this.code=Application.getSnowflakeIdWorker().nextId()+"";
	}
	public void fullImg(String imgServer) {
		// TODO Auto-generated method stub
		if (!this.description.contains("http")) {
			this.description = this.description.replace("<img src=\"", "<img src=\"" + imgServer);
		}
	}
	

}
