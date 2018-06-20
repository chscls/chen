package com.zhitail.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name = "fy_friend",uniqueConstraints = {
	      @UniqueConstraint(columnNames = {"user_id", "friend_id"})
	})
public class FyFriend {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Long getFriendId() {
		return friendId;
	}
	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
	public FyGroup getGroup() {
		return group;
	}
	public void setGroup(FyGroup group) {
		this.group = group;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	private String realname;
	@Column(name = "friend_id")
	private Long friendId;
	@ManyToOne
	@JoinColumn(name = "group_id")

	private FyGroup group;
}
