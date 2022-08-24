package org.woorin.catudy.mapper;

import java.sql.Time;

import org.apache.ibatis.annotations.Mapper;
import org.woorin.catudy.model.memberRoomAttendanceDTO;

@Mapper
public interface MemberRoomAttendanceMapper {
    // 공부 시작(출석), 데이터 생성
    int studyStart(int room_no, int member_no);

    // 한 유저의 출석정보를 조회
    memberRoomAttendanceDTO getMemberRoomAttendance(int room_no, int member_no);

    // 공부 종료(퇴실)
    int studyEnd(int room_no, int member_no, Time study_time);


    /// 아래 기능은 사용되지 않을 수 있습니다.

    // 유저 기록 삭제
    int deleteMemberAttendance(int member_no);

    // 방 기록 삭제
    int deleteRoomAttendance(int room_no);

    // 한 방에 속한 한 유저의 출석정보를 전부 삭제
    int deleteMemberRoomAttendance(int room_no, int member_no);
}
