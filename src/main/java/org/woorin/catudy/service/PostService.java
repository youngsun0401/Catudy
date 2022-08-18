package org.woorin.catudy.service;

import java.util.List;

import org.woorin.catudy.model.PostDTO;

public interface PostService {
	// 게시글 작성
	public void post_insert(PostDTO dto);
	// 게시글 삭제
	public PostDTO get_post(int post_no);// 게시글 조회
	public List<PostDTO> get_posts(String category, int page);// 게시글 목록 조회 category에 카테고리 이름, 혹은 all을 적으면 모든 카테고리 조회
	public List<PostDTO> get_posts_search(String searchval, int page);// 게시글 검색
	// 댓글 작성
	// 댓글 삭제
}
