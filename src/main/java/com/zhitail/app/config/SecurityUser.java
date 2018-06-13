/**
 * 
 */
package com.zhitail.app.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.zhitail.app.entity.FyUser;


/**
 * @author huangshun
 *
 */
public class SecurityUser  implements UserDetails{

	/**
	 * 用户ID
	 */
	private Long userid;
	
	/**
	 * 密码
	 */
	@JSONField(serialize=false)
	private String password;
	
	/**
	 * 用户名
	 */
	private String username;
	
	
	
	public SecurityUser(FyUser user,String password) {
		// TODO Auto-generated constructor stub
		this.setUsername(user.getMobile());
		this.setPassword(password);
		
	}
	
	@Override
	@JSONField(serialize=false) 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<String> perms=null;
		if(perms==null) {
			perms=new HashSet<String>();
		}
		String authorityString=String.join(",", perms.toArray(new String[] {}));
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
	}

	@Override
	@JSONField(serialize=false) 
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JSONField(serialize=false) 
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JSONField(serialize=false) 
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JSONField(serialize=false) 
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	@JSONField(serialize=false) 
	private String json;
	public void setJson(String json) {
		this.json = json;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.json;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	
}
