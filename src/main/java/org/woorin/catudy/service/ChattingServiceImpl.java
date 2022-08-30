package org.woorin.catudy.service;
/*
 * 채팅 설명

웹소켓을 통해 채팅을 주고받습니다.
회원이 스터디방에 입장하면, 클라이언트에서 웹소켓을 연결합니다.
웹소켓을 연결 할 때, 자신이 누구인지(회원번호)와 어느 방에 입장한 건지(스터디방번호)의 정보를 서버에 넘깁니다.
서버는 같은 방에 입장한 사람들끼리 리스트로 묶어서,
한 사람이 메시지를 보내면, 같은 방의 모든 사람에게 그 메시지를 전달합니다. 전달된 메시지는 클라이언트의 화면에 표시됩니다.

클라이언트에서 회원번호를 사칭하거나, 권한 없는 방에 입장하려는 시도에 대비해,
회원이 스터디방에 입장할 때, 채팅 대기열에 해당 회원번호와 스터디방번호를 기록해두었다가,
실제로 웹소켓 연결이 시도될 때, 웹소켓 연결 정보와 대기열을 비교하여, 일치하는 정보가 대기열에 있는 경우에만 연결을 수락합니다.
입장하려는 방번호, 회원번호, 임시 비밀번호가 일치해야 하는데 현재 비밀번호는 랜덤생성되지 않고 테스트용으로 "abc"를 쓰고 있습니다.

클라이언트는 채팅 메시지를 받을 때마다, 그 메시지의 정보를 토대로 말풍선 요소를 생성하여, 채팅창 요소 안에 삽입합니다.
새 말풍선이 삽입될 때마다 자동으로 채팅창 맨 아래로 스크롤됩니다.

 * */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MemberMapper;
import org.woorin.catudy.model.ChattingUser;

@Service
@ServerEndpoint(value="/chat")
public class ChattingServiceImpl implements ChattingService {

	private static Set<ChattingUser> waiting// 채팅 입장 대기열 (아무나 아무 방에나 마음대로 접속하면 안 되므로, 유효한 접근만 대기열에 넣고, 대기열에 대응하는 웹소켓 연결만 받아들인다.)
			= Collections.synchronizedSet(new HashSet<ChattingUser>());

	//// 채팅 참여자들의 목록 List<ChattingUser>이 채팅방에 해당한다.
	private static Map<Integer, List<ChattingUser>> study2chat      // (매핑: 스터디방번호 → 해당 스터디방의 채팅방)
		= Collections.synchronizedMap(new HashMap<Integer, List<ChattingUser>>());
	private static Map<Session, ChattingUser> s2user                // (매핑: 웹소켓 세션 → 해당 사용자)
		= Collections.synchronizedMap(new HashMap<Session, ChattingUser>());
	private static Map<ChattingUser, List<ChattingUser>> rooms      // (매핑: 사용자 →  속한 채팅방)
		= Collections.synchronizedMap(new HashMap<ChattingUser, List<ChattingUser>>());
		
	
	//// 웹소켓 연결 생성
	@OnOpen
	@Override
	public void onOpen(Session session) {
		System.out.println("채팅 세션 open: " + session.toString());

		//// 대기열에 있는 참여자이고 비밀번호가 맞는 경우에만 연결 수락
		ChattingUser u2 = new ChattingUser(// 회원번호, 방번호, 비밀번호로 참여자 객체 만들기
				member_no_of(session), // 
				room_no_of(session), 
				null,
				session.getRequestParameterMap().get("password").get(0),
				null);
		ChattingUser u1= checkWaiting(u2);// 대기열에 있는 유효한 참여자. 무효이면 null 
		if( u1 != null ){// 접근 유효하면
			enterChatting(u1, session);// 방에 들어감
		}
		else{// 부적절한 웹소켓 연결 시도
			try {
				session.close();
				// TODO 연결 끊으면 에러 나는데 왜 나느지 모를
			}
			catch (Exception e) {
			}
		}
	}
	
	//// 메시지 수신
	@OnMessage
	@Override
	public void onMessage(String msg, Session session) throws Exception{
		// TODO 속한 방의 모든 접속자에게 메시지 전송
		List<ChattingUser> room = rooms.get(s2user.get(session));
		
		String chat = oneChat(session, msg);
		System.out.println("보낼 채팅: "+chat);
		for(ChattingUser temp: room){// 세션이 속한 방에 속한 세션들 전체 순회하며
			temp.getSession().getBasicRemote().sendText(chat);// 메시지 전송 TODO 누구의 메시지인지 표시해야 함.
		}
	}
	
	//// 웹소켓 연결 끊김
	@OnClose
	@Override
	public void onClose(Session session) {
		System.out.println("채팅 세션 close: " + session);
		leaveChatting( session );
	}

	
	
	
	
	//// n번 방에 m번 회원 접속 대기
	@Override
	public void newWaiting(int room_no, int member_no, String password, String member_nick){
		waiting.add(
			new ChattingUser(member_no, room_no, null, password, member_nick)
		);
	}

	//// 적절한 연결 시도 검사
	////   대기열에 맞는 대기자가 있으면 그 대기자 반환,
	////   없으면 null 반환
	@Override
	public ChattingUser checkWaiting(ChattingUser u){
		for( ChattingUser temp: waiting ) {
			if( temp.getMember_no().equals(u.getMember_no())
			&&  temp.getRoom_no().equals(u.getRoom_no())
			&&  temp.getPassword().equals(u.getPassword())) {
				return temp;
			}
		}
		return null;
	}

	//// 방에 참여자 추가
	@Override
	public void enterChatting(ChattingUser u, Session session){
		u.setSession(session);// 유저로 세션 찾기 용 ???
		s2user.put(session, u);// 세션으로 유저 찾기 용???
		
		List<ChattingUser> room = study2chat.get(room_no_of(u.getSession()));// 참여할 방 (참가자-세션-방번호-방)

		if( room == null ){// 해당 방이 없으면
			room = new ArrayList<ChattingUser>();// 방 만들기
			study2chat.put( room_no_of(u.getSession()), room );// 만든 방을 방목록(스터디방번호->방 매핑)에 추가
			System.out.println("방을 만듦");
		}
		rooms.put( u, room );// (참여자->방) 매핑 추가
		// 그 방에 참여자 추가
		room.add(u);
		System.out.println("방에 사용자 추가. (아래 방 정보");
		System.out.println(room);
		
		waiting.remove(u);// 대기열에서 제거
	}

	//// n번 방에서 m번 회원 제거
	//// 방에서 참여자를 제거하고, 남은 인원이 없으면 방을 제거
	@Override
	public void leaveChatting(Session session){
		List<ChattingUser> room = rooms.get(s2user.get(session));// 해당 방
		rooms.remove(room);// 사용자-채팅방 매핑 제거
		room.remove(s2user.get(session));// 방에서 참여자 제거
		s2user.remove(session);// 세션-사용자 매핑 제거
		//// TODO 참여자 0명이면 방 제거
	}
	
	
	
	
	
	//// 말주머니 한 개 생성 (JSON)
	@Override
	public String oneChat(Session session, String msg) {
		return 
				"{\"member_no\":"+member_no_of(session)
				+", \"member_nick\":\""+s2user.get(session).getMember_nick()+"\""
				+", \"msg\":\""+msg+"\"}";
	}


	
	//// 세션으로 방번호 가져오기
	private int room_no_of(Session session){
		if( session == null ) return 0;
		return Integer.parseInt(session.getRequestParameterMap().get("room").get(0));
	}

	//// 세션으로 회원번호 가져오기
	private int member_no_of(Session session){
		if( session == null ) return 0;
		return Integer.parseInt(session.getRequestParameterMap().get("member").get(0));
	}

	//// 세션으로 회원 닉네임 가져오기
	private int member_nick_of(Session session){
		if( session == null ) return 0;
		return Integer.parseInt(session.getRequestParameterMap().get("member").get(0));
	}

	//// ??? 개선점: 대기열에서 영원히 제거되지 않을 수도 있을 듯.

}
