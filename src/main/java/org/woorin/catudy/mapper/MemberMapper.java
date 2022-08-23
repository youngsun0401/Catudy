package org.woorin.catudy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.woorin.catudy.model.AttendDTO;
import org.woorin.catudy.model.MemberDTO;

/**
 * MainMapper에서 회원과 관련된 기능을 분리중인 파일입니다.
 * 당장 기능을 바꿀수 없기 때문에 우선 주석처리하고, 한 메서드씩 기능을 옮깁니다.
 */

@Mapper
public interface MemberMapper {
    // // 회원가입
    // public void member_insert(MemberDTO dto);

    // // member 정보 1개 가져오기
    // MemberDTO memberOneSelect(String member_id);

    // // 로그인 비밀번호 매치
    // String memberRealPassword(String member_id);

    // // 회원가입 아이디 중복체크
    // int memberIdCheck(String member_id);

    // // 회원가입 로그인 중복체크
    // int memberNickCheck(String member_nick);

    // public void member_delete(int member_no);

    // 한 사용자의 정보를 조회합니다.
    MemberDTO member_find(int member_no);

    // 방에 참석한 팀원의 정보(번호 등)를 가져옵니다.
    ArrayList<Integer> attended_member(int room_no);
}
