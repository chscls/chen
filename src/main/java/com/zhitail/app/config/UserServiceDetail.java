package com.zhitail.app.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyUserMng;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private FyUserMng userMng;
   
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	SecurityUser securityUser=null;
    	FyUser user=this.userMng.findByUserName(username);
    	
    	if(user!=null) {
    		securityUser=new SecurityUser(user, user.getPassword());
    	}
        return securityUser;
    }
}
