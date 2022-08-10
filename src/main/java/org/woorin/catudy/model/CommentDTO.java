package org.woorin.catudy.model;
/* 댓글 */

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer comment_no;// 댓글번호
    private Integer comment_target_comment;// 어느 댓글에 대한 답글인가 (null이면 그냥 댓글)
    private String comment_content;// 댓글내용
    private MemberDTO comment_writer;// 댓글 작성자
    private Timestamp comment_regdate;// 댓글등록일
}
