package com.zhitail.app.websocket;

public class WsMsg<T>{
	private String type;
	private Long userId;
	private T body;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
}
