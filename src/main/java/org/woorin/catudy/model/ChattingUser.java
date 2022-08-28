package org.woorin.catudy.model;

import javax.websocket.Session;

//// 웹소켓에서 http세션을 바로 읽어오지 못하므로, 로그인한 회원과 웹소켓 세션을 함께 관리할 용도의 클래스
public class ChattingUser {
	private Integer member_no;// 회원 번호
	private Integer room_no;// 채팅이 속한 방 번호
	private Session session;// 웹소켓 세션 
	private String password;
	private String member_nick;

	public ChattingUser(int member_no, int room_no, Session session, String password, String member_nick){
		this.member_no = member_no;
		this.room_no = room_no;
		this.session = session;
		this.password = password;
		this.member_nick = member_nick;
	}

	public Integer getMember_no() {
		return member_no;
	}

	public Integer getRoom_no() {
		return room_no;
	}

	public String getPassword() {
		return password;
	}

	public String getMember_nick() {
		return member_nick;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		this.session = s;
	}
}
