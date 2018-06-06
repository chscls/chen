package com.zhitail.app.soa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyUser;

@Component
public class OpenIdManager {
	 @Autowired
	    private StringRedisTemplate stringRedisTemplate;


	/**
	 * 根据用户获取Token
	 * @param user
	 * @return
	 */
	public synchronized String getOpenId(String sessionId){
		String  openId=null;
		
		if(this.isAlready(sessionId)){
			openId=stringRedisTemplate.opsForValue().get(sessionId);
		}else{
			
			
			
			
		 openId= generateToken(sessionId);
		 if(openId!=null) {
		 stringRedisTemplate.opsForValue().set(sessionId, openId);
		 }
		
		}
		return openId;
	}
	private String generateToken(String sessionId) {
		// TODO Auto-generated method stub
		HttpClient client = HttpClients.createDefault();
		String urlStr = "https://api.weixin.qq.com/sns/jscode2session";
		
		  List<NameValuePair> params = new ArrayList();  
		  params.add(new BasicNameValuePair("appid", "wx26b77a934c0f8a53"));  
		  params.add(new BasicNameValuePair("secret","735a6ebc880d367410f6cf9c97236e65")); 
		  
	      params.add(new BasicNameValuePair("js_code", sessionId));  
	      params.add(new BasicNameValuePair("grant_type","authorization_code"));  
		  HttpResponse response;
		  try {
		  String  str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));  
			 HttpGet get = new HttpGet(urlStr+"?"+str );
			 response = client.execute(get);
		      HttpEntity entity = response.getEntity();
		     String  token = EntityUtils.toString(entity, "UTF-8");
		   JSONObject jo=   JSONObject.parseObject(token);
		    
		     return jo.getString("openid");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
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
	
	

	
	

	
}
