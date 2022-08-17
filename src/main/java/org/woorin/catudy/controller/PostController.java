package org.woorin.catudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {
    
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
}
