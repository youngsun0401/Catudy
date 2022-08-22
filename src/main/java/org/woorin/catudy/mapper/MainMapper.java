package org.woorin.catudy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.woorin.catudy.model.*;

import java.util.List;

@Mapper
public interface MainMapper {

    String testSelect(int p_id);

    // 회원가입
    public void member_insert(MemberDTO dto);

    // member 정보 1개 가져오기
    MemberDTO memberOneSelect(String member_id);

    // 로그인 비밀번호 매치
    String memberRealPassword(String member_id);

    // 회원가입 아이디 중복체크
    int memberIdCheck(String member_id);

    // 회원가입 로그인 중복체크
    int memberNickCheck(String member_nick);

    public void member_delete(int member_no);

    // 스터디방 개설
    void room_insert(RoomDTO dto);
    // 스터디방 목록
    public RoomDTO room_select(int room_no);
    // 스터디방 목록
    List<RoomDTO> room_list();


    public List<RoomDTO> room_select_orderbyNo(int offset, int limit);

    public List<RoomDTO> room_select_orderbyNo_onlyOpen(int offset, int limit);
//	public void room_insert(RoomDTO dto);
//	public void room_update(RoomDTO dto);
//	public void room_delete(Integer room_no);

    public DocumentDTO document_select(Integer document_no);
//	public void document_insert(DocumentDTO dto);
//	public void document_update(DocumentDTO dto);
//	public void document_delete(Integer document_no);

    public PostDTO post_select(int post_no);// 특정 게시물

    public List<PostDTO> post_select_orderbyNo_all(int offset, int limit);// 전체 카테고리 목록

    public List<PostDTO> post_select_orderbyNo_category(String category, int offset, int limit);// 특정 카테고리 목록

    public void post_insert(PostDTO dto);

    public void post_update(PostDTO dto);

    public void post_delete(Integer post_no);

    public List<CommentDTO> comments_on_post(Integer post_no);// 게시글의 댓글 목록


}
