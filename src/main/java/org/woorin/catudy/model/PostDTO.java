package org.woorin.catudy.model;
/* 게시글 */

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
    private Integer post_no;// 번호
    private String post_title;// 제목
    private String post_category;// 카테고리 (r: 모집 / 0: 기타)
    private String post_content;// 내용
    private String post_writer;// 글쓴이
    private Timestamp post_regdate;// 등록일
    private Integer post_viewcount;// 조회수
    private List<CommentDTO> comments;// 댓글
}
