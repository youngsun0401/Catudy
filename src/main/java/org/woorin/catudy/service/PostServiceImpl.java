package org.woorin.catudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.PostDTO;

@Service
public class PostServiceImpl implements PostService {
	
	private static int pageSize = 5;// 한 페이지에 보여줄 게시글 수
	
	@Autowired private MainMapper mapper;

	@Override
	public PostDTO get_post(int post_no) {
		PostDTO post = mapper.post_select(post_no);// 게시글 불러오기
		post.setComments(mapper.comments_on_post(post_no));// 게시글의 댓글 불러오기
		return post;
	}

	//// 게시글 목록 조회
	////  category에 카테고리 이름으로 해당 카테고리 조회
	////  category에 all을 적으면 모든 카테고리 조회
	@Override
	public List<PostDTO> get_posts(String category, int page) {
		if( category.equals("all") )// 카테고리 전체 보기
			return mapper.post_select_orderbyNo_all(pageSize*page, pageSize);
		else // 특정 카테고리 
			return mapper.post_select_orderbyNo_category(category, pageSize*page, pageSize);
	}

	//// 게시글 검색
	@Override
	public List<PostDTO> get_posts_search(String searchval, int page) {
		return null;
	}

	// 게시글 등록
	@Override
	public void post_insert(PostDTO dto) {
		mapper.post_insert(dto);
	}

	// 게시글 상세보기
	@Override
	public PostDTO post_select(int post_no) {
		return mapper.post_select(post_no);
	}

	@Override
	public void post_view_count(int post_no) {
		mapper.post_view_count(post_no);
	}

}
