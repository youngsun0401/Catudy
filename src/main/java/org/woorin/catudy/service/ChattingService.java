package org.woorin.catudy.service;

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

import org.springframework.stereotype.Service;
import org.woorin.catudy.model.ChattingWaiter;

@Service
@ServerEndpoint(value="/chat")
public class ChattingService {

	private static Set<ChattingWaiter> waiting// 채팅 입장 대기열 (아무나 아무 방에나 마음대로 접속하면 안 되므로, 유효한 접근만 대기열에 넣고, 대기열에 대응하는 웹소켓 연결만 받아들인다.)
			= Collections.synchronizedSet(new HashSet<ChattingWaiter>());

	//// 채팅 참여자의 세션들의 목록 List<Session>이 채팅방에 해당한다.
	private static Map<Integer, List<Session>> study2chat // 채팅방 (매핑: 스터디방번호 → 해당 스터디방의 채팅방)
			= Collections.synchronizedMap(new HashMap<Integer, List<Session>>());
	private static Map<Session, List<Session>> rooms // 채팅방 (매핑: 웹소켓 세션 → 해당 사용자가 속한 채팅방)
			= Collections.synchronizedMap(new HashMap<Session, List<Session>>());
	
	
	//// 웹소켓 연결 생성
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("채팅 세션 open: " + session.toString());
		System.out.println("아아아아아");

		//// TODO 대기열에 있는 회원이고 비밀번호가 맞는 경우에만 연결 수락
		ChattingWaiter u = new ChattingWaiter(// 회원번호, 방번호, 비밀번호로 참여자 객체 만들기
				member_no_of(session), // 
				room_no_of(session), 
				session.getRequestParameterMap().get("password").get(0));

		System.out.print("접근 유효? ");
		if( checkWaiting(u) ){
			System.out.println("유효: 대기열에서 제거, 방에 입장");
			// TODO 세션 등록
			waiting.remove(u);// 대기열에서 제거
			enterChatting(session);// 방에 들어감
		}
		else{// 부적절한 웹소켓 연결 시도
			System.out.println("무효");
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
	public void onMessage(String msg, Session session) throws Exception{
		// TODO 속한 방의 모든 접속자에게 메시지 전송
		List<Session> room = rooms.get(session);// TODO 이거 안 되냐?
		for(Session temp: room){// 세션이 속한 방에 속한 세션들 전체 순회하며
			temp.getBasicRemote().sendText(msg);// 메시지 전송 TODO 누구의 메시지인지 표시해야 함.
		}
	}
	
	//// 웹소켓 연결 끊김
	@OnClose
	public void onClose(Session session) {
		System.out.println("채팅 세션 close: " + session);
		leaveChatting( session );// 방에서 해당 회원 제거
	}

	//// n번 방에 m번 회원 접속 대기
	public static void newWaiting(int room_no, int member_no, String password){
		waiting.add(
			new ChattingWaiter(room_no, member_no, password)
		);
	}

	//// 적절한 연결 시도 검사
	public static boolean checkWaiting(ChattingWaiter u){
		System.out.print("채팅 대기열: 일치하는 요소 찾기 …");
		for( ChattingWaiter temp: waiting ) {
			if( temp.getMember_no().equals(u.getMember_no())
			&&  temp.getRoom_no().equals(u.getRoom_no())
			&&  temp.getPassword().equals(u.getPassword())) {
				System.out.println(" 찾음");
				return true;
			}
		}
		System.out.println(" 못 찾음");
		return false;
	}

	//// 방에 참여자 추가
	public static void enterChatting(Session session){
		System.out.println("방에 참여자 추가 시도");
		List<Session> room = study2chat.get(room_no_of(session));// 참여할 방

		if( room == null ){// 해당 방이 없으면
			System.out.print("방을 만듭니다.");
			room = new ArrayList<Session>();// 방 만들기
			study2chat.put( room_no_of(session), room );// 만든 방을 방목록(스터디방번호->방 매핑)에 추가
			System.out.println("방을 만들었습니다.");
		}
		rooms.put( session, room );// 만든 방을 방목록(세션->방 매핑)에 추가
		// 그 방에 참여자 추가
		room.add(session);
		System.out.println("방에 사용자 추가. (아래 방 정보");
		System.out.println(room);
	}

	//// n번 방에서 m번 회원 제거
	//// TODO 방에서 참여자를 제거하고, 남은 인원이 없으면 방을 제거
	public static void leaveChatting(Session session){
		List<Session> room = rooms.get(session);// 해당 방
		room.remove(session);// 방에서 참여자 제거
		//// TODO 참여자 0명이면 방 제거
	}


	
	//// 세션으로 방번호 가져오기
	private static int room_no_of(Session session){
		if( session == null ) return 0;
		return Integer.parseInt(session.getRequestParameterMap().get("room").get(0));
	}

	//// 세션으로 회원번호 가져오기
	private static int member_no_of(Session session){
		if( session == null ) return 0;
		return Integer.parseInt(session.getRequestParameterMap().get("member").get(0));
	}

	//// ??? 개선점: 대기열에서 영원히 제거되지 않을 수도 있을 듯.
}
