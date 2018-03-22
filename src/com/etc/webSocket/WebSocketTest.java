package com.etc.webSocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketTest {
	private static int onlineCount=0;
	
	private static CopyOnWriteArraySet<WebSocketTest> webSocketSet=new CopyOnWriteArraySet<WebSocketTest>();
	
	private Session session;
	
	@OnOpen
	public void onOpen(Session session){
		this.session=session;
		webSocketSet.add(this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
	}
	
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);
		subOnlineCount();
		System.out.println("有一连接关闭！当前在线人数为"+getOnlineCount());
	}
	
	@OnMessage
	public void onMessage(String data,Session session){
		System.out.println("来自客户端的消息："+data);
		for(WebSocketTest item:webSocketSet){
			try{
				item.sendMessage(data);
			}catch(IOException e){
				e.printStackTrace();
				continue;
			}
		}
	}
	
	@OnError
	public void onError(Session session,Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
	}
	
	public static synchronized int getOnlineCount(){
		return onlineCount;
	}
	
	public static synchronized void addOnlineCount(){
		WebSocketTest.onlineCount++;
	}
	
	public static synchronized void subOnlineCount(){
		WebSocketTest.onlineCount--;
	}
}
