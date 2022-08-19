package org.woorin.catudy.service;

import org.woorin.catudy.model.MemberDTO;


public interface MemberService {
	// 회원 가입
	void member_insert(MemberDTO dto);
	// 회원 탈퇴
	public void member_delete(Integer member_no);
	// 회원 로그인
	MemberDTO member_login(MemberDTO dto);
	// 회원가입 아이디 중복체크
	int memberIdCheck(String member_id);

	// 회원가입 닉네임 중복체크
	int memberNickCheck(String member_nick);


	// 회원정보 수정인
	// 
	// 
	// 
	// 
	// 
}
