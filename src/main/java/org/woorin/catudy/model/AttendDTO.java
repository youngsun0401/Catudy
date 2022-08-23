package org.woorin.catudy.model;
/* 참여 정보 */

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AttendDTO {
	private int attend_target_member;// -- 이 회원이
	private int attend_target_room;// -- 이 스터디방에 참여함
	private String attend_comment;// -- 이 스터디에서의 각오나 목표 등 개인 메모
	private Timestamp attend_regdate;// -- 참여날짜
}
