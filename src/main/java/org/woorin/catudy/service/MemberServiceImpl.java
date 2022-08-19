package org.woorin.catudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MainMapper mapper;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;


	// 회원 등록
	@Override
	public void member_insert(MemberDTO dto) {

		System.out.println("회원가입 - 비밀번호 암호화 중.");
		dto.setMember_pw(pwEncoder.encode(dto.getMember_pw()));
		mapper.member_insert(dto);
		System.out.println("회원가입 및 비밀번호 암호화 완료.");
	}
	// 회원가입 아이디 중복 체크
	@Override
	public int memberIdCheck(String member_id) {
		int cnt = mapper.memberIdCheck(member_id);
		return cnt;
	}

	// 회원가입 닉네임 중복 체크
	@Override
	public int memberNickCheck(String member_nick) {
		int nickCnt = mapper.memberNickCheck(member_nick);
		return nickCnt;
	}
	
	// 회원 삭제
	@Override
	public void member_delete(Integer member_no) {
	}


	// 회원 로그인
	@Override
	public MemberDTO member_login(MemberDTO dto) {
		String resultPW = mapper.memberRealPassword( dto.getMember_id() );// 입력된 이메일로 회원정보 찾기
		boolean loginFilter = pwEncoder.matches(dto.getMember_pw(), resultPW);
		System.out.println(loginFilter);
		System.out.println("resultPW 값 : " + resultPW);

		if (loginFilter) {

			MemberDTO resultID = mapper.memberOneSelect(dto.getMember_id());

			return resultID;
		} else {
			return null;
		}

	}


}
