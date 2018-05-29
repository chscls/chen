package com.zhitail.app.websocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;





@ServerEndpoint(value = "/qcodeWebSocket")
@Component
public class QcodeWebSocket {
    
  
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
 
    //private static Map<String,MyWebSocket> wss = new ConcurrentHashMap<String,MyWebSocket>();

    private static CacheMap<Object,Object> wss = CacheMap.getDefault();
    @OnOpen
    public void onOpen(Session session) {
       
     
        try {
        	JSONObject x = new JSONObject();
        	x.put("type", "/qcode/connectSuc");
        	WsMsg wm =new WsMsg();
        	wm.setType("connectSuc");
        	
        	session.getBasicRemote().sendText(JSONObject.toJSONString(wm));
        
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    
             //在线数减1

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
    	  WsMsg<String> req = JSONObject.parseObject(message, WsMsg.class);
         //System.out.println(message);
         if(req.getType().equals("/qcode/login")){
        	 String code = req.getBody();
        	 wss.put(code, this);
         }
         
     	WsMsg wm =new WsMsg();
     	wm.setType("/qcode/loginSuc");
     	
     	send(wm,session);
        
         
    }
    public void send(WsMsg message, Session session){
    	try {
			session.getBasicRemote().sendText(JSONObject.toJSONString(message));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


   

}