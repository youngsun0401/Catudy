package org.woorin.catudy.model;

import javax.websocket.Session;

//// 웹소켓에서 http세션을 바로 읽어오지 못하므로, 로그인한 회원과 웹소켓 세션을 함께 관리할 용도의 클래스
public class ChattingWaiter {
	private Integer member_no;// 회원 번호
	private Integer room_no;// 채팅이 속한 방 번호
	private String password;

	public ChattingWaiter(int member_no, int room_no, String password){
		this.member_no = member_no;
		this.room_no = room_no;
		this.password = password;
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
}
