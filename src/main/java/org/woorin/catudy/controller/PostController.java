package org.woorin.catudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.woorin.catudy.model.PostDTO;
import org.woorin.catudy.service.PostService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;
    
    // 모집 및 커뮤니티 페이지
    @GetMapping("/recruit")
    public String recruit(Model model) {
		System.out.println("모집 및 커뮤니티 페이지");
		model.addAttribute("posts", postService.get_posts("all", 0));// 게시글 목록 (일단 전체 카테고리)
        return "board/recruit";
    }

    // 게시글 작성 페이지
    @GetMapping("/write")
    public String write() {
        System.out.println("게시글 작성 페이지");
        return "board/write";
    }

    // 게시글 작성
    @PostMapping("/post_insert")
    public String post_insert(PostDTO dto, HttpSession session) {
        dto.setPost_writer(loginId(session));// 글쓴이 = 현재 로그인한 회원
        postService.post_insert(dto);
        return "redirect:/recruit";
    }
    
    
    
    
	// 현재 로그인한 회원 번호(정수) 가져오기
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("member_no") == null ) return 0;
		return Integer.parseInt( session.getAttribute("member_no")+"" );
	}
}
