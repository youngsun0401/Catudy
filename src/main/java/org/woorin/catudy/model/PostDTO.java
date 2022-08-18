package org.woorin.catudy.model;
/* 게시글 */

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
    private Integer post_no;// 번호
    private String post_title;// 제목
    private String post_category;// 카테고리 (rec: 모집중 / clo : 모집완료 / qna : 질답 / com : 커뮤니티)
    private String post_content;// 내용
    private Integer post_writer;// 글쓴이
    private MemberDTO post_writer_dto;// 글쓴이 정보
    private Timestamp post_regdate;// 등록일
    private Integer post_viewcount;// 조회수
    private List<CommentDTO> comments;// 댓글
}
