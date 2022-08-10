package org.woorin.catudy.model;
/* 서랍글 */

import lombok.Data;

@Data
public class DocumentDTO {
	private Integer document_no;// -- 번호
	private RoomDTO document_target;// 이 글이 속한 스터디방
	private MemberDTO document_writer;// -- 글쓴이
	private String document_content;// -- 내용
	private Character document_type;// 종류(a: 일반, i: 이미지)
	private Image image;// 첨부 이미지
}
