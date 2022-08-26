package org.woorin.catudy.service;

import java.util.List;

import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;


public interface MemberService {
	// 회원 가입
	void member_insert(MemberDTO dto);
	// 회원 탈퇴
	public void member_delete(Integer member_no);
	// 회원 로그인
	MemberDTO member_login(MemberDTO dto);
	// 회원가입 아이디 중복체크
	int memberIdCheck(String member_id);
	// 회원 목록
	List<MemberDTO> member_select();

	// 회원가입 닉네임 중복체크
	int memberNickCheck(String member_nick);

	// 회원(비회원) 사용자가 다른 사용자의 정보를 조회함.
	// 비밀번호같은 민감한 정보는 가져오지 않습니다.
	MemberDTO member_find(int member_no);

	// 한 방에 있는 팀원의 목록을 가져옵니다.
    List<MemberDTO> member_list_on_a_room(int room_no);



	// 회원정보 수정인
	// 
	// 
	// 
	// 
	// 
}
