package org.woorin.catudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 화면 이동
    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    // 회원가입
    @PostMapping("/member_insert")
    public String member_insert(MemberDTO dto){
        memberService.member_insert(dto);
        return "redirect:/";
    }

    // 로그인 화면 이동
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 멤버 로그인

}
