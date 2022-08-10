package org.woorin.catudy.model;
/* 스터디방 */

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class RoomDTO {
    private Integer room_no;// 스터디방 번호
    private String room_title;// 방제목
    private String room_category;// 카테고리 (a: 어학, b: 취업 뭐 이런식으로???)
    private Integer room_ruler;// 방장
    private String room_introduce;// 소개글
    private String room_rule;// 규칙글
    private Timestamp room_regdate;// 생성일
    private Boolean room_open;// 참여신청 받기 여부
    private List<String> tags;// 태그
}
