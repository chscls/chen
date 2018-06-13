package com.zhitail.app.oauth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zhitail.app.entity.FyUser;
import com.zhitail.app.manager.FyUserMng;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by lxg
 * on 2017/2/20.
 */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private FyUserMng userMng;

 
    /**
     * 根据用户名获取登录用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FyUser user = userMng.findByUserName(username);

        if(user == null){
             throw new UsernameNotFoundException("用户名："+ username + "不存在！");
        }
        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
       
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),collection);
    }
}
