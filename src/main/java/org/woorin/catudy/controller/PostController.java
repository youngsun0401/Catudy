package org.woorin.catudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String recruit() {
		System.out.println("모집 및 커뮤니티 페이지");
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
    public String post_insert(PostDTO dto) {
        System.out.println("작성 시작.");
        postService.post_insert(dto);
        System.out.println("작성 완료.");
        return "redirect:/recruit";
    }
}
