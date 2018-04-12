package com.zhitail.app.soa;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;



import com.zhitail.app.entity.FyUser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LoginManager {
	 @Autowired
	    private StringRedisTemplate stringRedisTemplate;


	/**
	 * 根据用户获取Token
	 * @param user
	 * @return
	 */
	public synchronized String getToken(FyUser user){
		String  token;
		String uid=user.getId()+"";
		if(this.isAlready(uid)){
			token=stringRedisTemplate.opsForValue().get(uid);
		}else{
		 token= generateToken(user);
		 stringRedisTemplate.opsForValue().set(uid, token);
		stringRedisTemplate.opsForValue().set(token,user.getMobile());
		}
		return token;
	}
	private synchronized Boolean isAlready(String uid){
		return stringRedisTemplate.hasKey(uid);
	}
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	public synchronized boolean verify(String token){
		if(StringUtils.isBlank(token)){
			return false;
		}
		
		return stringRedisTemplate.hasKey(token);
	
	}
	
	

	public String getUser(String token){
		if(token==null){
			return null;
		}
		String username= stringRedisTemplate.opsForValue().get(token);
		return  username;
	
	}
	
	

	
	
	private String generateToken(FyUser user){
		return UUID.randomUUID().toString().substring(0, 16);
	}
	
	
	
	
}
