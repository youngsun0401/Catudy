package org.woorin.catudy.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

@Service
@ServerEndpoint(value="/chat")
public class ChattingService {
	private static Set<Session> clients = 
			Collections.synchronizedSet(new HashSet<Session>());
	// 클라이언트가 어느 방에 입장한 건지 어케 구별???
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("채팅 세션 open: " + session.toString());
		if(!clients.contains(session)) {
			clients.add(session);
			System.out.println("session open : " + session + ", " + session.getRequestParameterMap().get("a")+"번 방");
		}else {
			System.out.println("이미 연결된 session 임!!!");
		}
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) throws Exception{
		System.out.println("receive message : " + msg);
		for(Session s : clients) { // 채팅에 접속한 모든 클라이언트들에게
			System.out.println("채팅 메시지: " + msg);
			s.getBasicRemote().sendText(msg); // 메시지 전송
		}		
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("채팅 세션 close: " + session);
		clients.remove(session);
	}
}
