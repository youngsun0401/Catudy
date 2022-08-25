package org.woorin.catudy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.woorin.catudy.model.MemberRoomAttendanceDTO;

public interface RoomAttendance {
    // 공부 시작
    int studyStart(int room_no, int member_no);

    // 공부 종료
    int studyEnd(int room_no, int member_no);

    // 공부시간 조회
    List<MemberRoomAttendanceDTO> getMemberRoomAttendance(int room_no, int member_no);

    /// 서비스단에서 재사용할 기능

    /** 
     * @serial
     * 공부를 시작했는지, 종료되지 않은 기록이 있는지 확인합니다.
     * @param room_no : 방 번호를 지정합니다.
     * @param member_no : 사용자 식별번호를 지정합니다.
     * @return 모든 기록 중 스터디가 진행(종료되지 않은)중인 방이 있으면 1(비정상)을반환, 진행/종료가 전부 완료되면 0(정상)을 반환합니다.
    */
    int memberRoomAttendCleaned(int room_no, int member_no);

}