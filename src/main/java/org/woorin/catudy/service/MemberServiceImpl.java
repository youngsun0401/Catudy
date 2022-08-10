package org.woorin.catudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired private MainMapper mapper;
	
	// 회원 등록
	@Override
	public void member_regist(MemberDTO dto) {
		mapper.member_insert(dto);
	}
	
	// 회원 삭제
	@Override
	public void member_delete(Integer member_no) {
		mapper.member_delete(member_no);
	}
	
	
}
