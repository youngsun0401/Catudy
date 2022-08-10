package org.woorin.catudy.model;
/* 회원 */

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberDTO {
	private Integer member_no;// 유저번호
	private String member_id;// 아이디
	private String member_pw;// 비밀번호
	private String member_nick;// 닉네임
	private String member_comment;// 각오한마디, 상태메세지 등 자유롭게 쓸 말
	private Timestamp member_regdate;// 등록일
}
