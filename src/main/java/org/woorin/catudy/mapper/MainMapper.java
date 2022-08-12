package org.woorin.catudy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.woorin.catudy.model.CommentDTO;
import org.woorin.catudy.model.DocumentDTO;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.PostDTO;

import java.util.List;

@Mapper
public interface MainMapper {

	String testSelect(int p_id);
	// 회원가입
	public void member_insert(MemberDTO dto);
	// 로그인
	void member_login(MemberDTO dto);


//	public RoomDTO room_select(Integer room_no);
//	public void room_insert(RoomDTO dto);
//	public void room_update(RoomDTO dto);
//	public void room_delete(Integer room_no);

	public DocumentDTO document_select(Integer document_no);
//	public void document_insert(DocumentDTO dto);
//	public void document_update(DocumentDTO dto);
//	public void document_delete(Integer document_no);

	public PostDTO post_select(int post_no);
	public void post_insert(PostDTO dto);
	public void post_update(PostDTO dto);
	public void post_delete(Integer post_no);
	public List<CommentDTO> comments_on_post(Integer post_no);// 게시글의 댓글 목록



}
