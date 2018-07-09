package com.zhitail.app.entity.middle;

public class ShowAble {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTemplete() {
		return templete;
	}
	public void setTemplete(String templete) {
		this.templete = templete;
	}
	private String img;
	private String path;
	private String templete;

}
