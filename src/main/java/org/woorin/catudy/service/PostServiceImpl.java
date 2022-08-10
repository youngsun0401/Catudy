package org.woorin.catudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.PostDTO;

@Service
public class PostServiceImpl implements PostService {
	@Autowired private MainMapper mapper;

	@Override
	public PostDTO get_post(int post_no) {
		PostDTO post = mapper.post_select(post_no);// 게시글 불러오기
		post.setComments(mapper.comments_on_post(post_no));// 게시글의 댓글 불러오기
		return post;
	}

}
