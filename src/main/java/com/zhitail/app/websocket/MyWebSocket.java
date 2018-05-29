package com.zhitail.app.websocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;



@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    private static Map<Long,MyWebSocket> wss = new ConcurrentHashMap<Long,MyWebSocket>();

    public static Map<Long, MyWebSocket> getWss() {
		return wss;
	}

	public static void setWss(Map<Long, MyWebSocket> wss) {
		MyWebSocket.wss = wss;
	}



    @OnOpen
    public void onOpen(Session session) {
       
     
        try {
        	JSONObject x = new JSONObject();
        	x.put("type", "connectSuc");
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
        webSocketSet.remove(this);  //从set中删除
             //在线数减1

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        
      System.out.println(message);
      
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