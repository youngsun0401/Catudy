package org.woorin.catudy.service;

import org.woorin.catudy.model.MemberDTO;


public interface MemberService {
	// 회원 가입
	void member_insert(MemberDTO dto);
	// 회원 탈퇴
	public void member_delete(Integer member_no);
	// 회원 로그인
	MemberDTO member_login(MemberDTO dto);



	// 회원정보 수정인
	// 
	// 
	// 
	// 
	// 
}
