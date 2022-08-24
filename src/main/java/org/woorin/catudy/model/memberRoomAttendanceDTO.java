package org.woorin.catudy.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class memberRoomAttendanceDTO {
    int attendance_room_no; // 방 번호
    int attendance_member_no; // 사용자 번호
    Timestamp attendance_study_start_time; // 시작시간
    Timestamp attendance_study_end_time; // 종료시간
    Timestamp attendance_study_time; // 하루 공부시간 합계
    int attendance_day_in_row; // 연속 출석횟수
}
