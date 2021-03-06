package com.zhitail.app.soa.wx;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bytebuddy.jar.asm.commons.TryCatchBlockSorter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Application;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyUser.Type;
import com.zhitail.app.manager.FyUserMng;
import com.zhitail.app.soa.LoginManager;

import com.zhitail.app.websocket.QcodeWebSocket;
import com.zhitail.app.websocket.WsMsg;
import com.zhitail.frame.common.annotion.TokenAuth;
import com.zhitail.frame.util.service.Result;

@RequestMapping("/wx/FyUserSvc")
@RestController
public class FyUserSvc {
	@Autowired
	private LoginManager loginManager;
	
	@Autowired
	private FyUserMng userMng;
	
	
	
	
	@RequestMapping(value = "/qcodeLogin",method=RequestMethod.GET)
	public Result qcodeLogin(String qcode,String openid ) {
		
		
		QcodeWebSocket qq= (QcodeWebSocket) QcodeWebSocket.wss.get(qcode);
		if(qq==null){
			return Result.error("二维码登录已过期,请刷新网页并重新扫描");
		}
		WsMsg<Map> wm =new WsMsg<Map>();
		wm.setType("/qcode/wxloginSuc");
     	
     	 FyUser u =   userMng.findByOpenid(openid);
     	Map<String,String> jo = new HashMap<String,String>();
		jo.put("status", "ok");
		jo.put("currentAuthority", u.getType().toString());
		String token = loginManager.getToken(u);
		jo.put("token", token);
		
		   
     	wm.setBody(jo);
     	qq.send(wm);
		
     	QcodeWebSocket.wss.remove(qcode);
     
		return new Result("login:ok");
		
		
		
		
		
	}
	
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	public Result wxcode(FyUser user) {
		   user.setPassword("123456");
		   user.setType(Type.user);
		   user.setMobile(Application.getSnowflakeIdWorker().nextId()+"");
		user = userMng.save(user);
		String t = loginManager.getToken(user);
		   user.setToken(t);
		   
		  return new Result(user);
		
		
		
		
	}
	@TokenAuth(value="token")
	@RequestMapping(value = "/wxcode",method=RequestMethod.POST)
	public Result wxcode(String code) {
		
		

		     String openid =  generateToken(code) ;
		     if(openid==null) {
		    	return Result.error("微信服务器错误"); 
		     }
		   FyUser user =   userMng.findByOpenid(openid);
		   if(user==null){
			  
			   user =  new FyUser();
			   user.setOpenid(openid);
		
			   return new Result(user);
		   }else{
			   String t = loginManager.getToken(user);
			   user.setToken(t);
			   user.setOpenid(openid);
			   return new Result(user);
		   }
		     
	
		
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
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		
	}
	
}
