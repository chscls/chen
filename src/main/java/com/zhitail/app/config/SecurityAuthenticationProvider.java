/**
 * 
 */
package com.zhitail.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @author huangshun
 *
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private UserServiceDetail userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username=authentication.getName();
		String password=(String)authentication.getCredentials();
		SecurityUser securityUser=(SecurityUser)this.userService.loadUserByUsername(username);
		if(securityUser==null) {
			throw new BadCredentialsException("账号不存在!");
		}
		if (!securityUser.getPassword().equals(password)) {
			throw new BadCredentialsException("密码不正确!");
		}
		/*if (!Md5PwdEncoder.getInstance().isPasswordValid(securityUser.getPassword(), password)) {
			throw new BadCredentialsException("密码不正确!");
		}*/
		securityUser.setJson(username);
		securityUser.setJson(JSONObject.toJSONString(securityUser));
		Authentication au=new UsernamePasswordAuthenticationToken(securityUser, securityUser.getPassword(), securityUser.getAuthorities());
		return au;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

	
}
