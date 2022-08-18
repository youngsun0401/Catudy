package org.woorin.catudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MainMapper mapper;

    // 회원가입 화면 이동
    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    // 회원가입
    @PostMapping("/member_insert")
    public String member_insert(MemberDTO dto) {
        memberService.member_insert(dto);
        return "redirect:/";
    }

    // 로그인 화면 이동
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 멤버 로그인
    @PostMapping("/member_login")
    public String member_login(MemberDTO dto, HttpServletRequest request, RedirectAttributes rttr) {

        MemberDTO resultDTO = memberService.member_login(dto);
        System.out.println("resultDTO 값 : " + resultDTO);

        if (resultDTO != null) {

            System.out.println("아이디 : " + resultDTO.getMember_id());
            System.out.println("레벨 : " + resultDTO.getMember_nick());
            System.out.println("닉네임 : " + resultDTO.getMember_comment());

            HttpSession session = request.getSession();
            session.setAttribute("member_no", resultDTO.getMember_no());
            session.setAttribute("member_id", resultDTO.getMember_id());
            session.setAttribute("member_nick", resultDTO.getMember_nick());
            session.setAttribute("member_comment", resultDTO.getMember_comment());


            System.out.println(resultDTO.getMember_nick());

            System.out.println("로그인 성공 / 세션 등록 완료.");
            return "redirect:/";
        } else {
            rttr.addFlashAttribute("msg", false);
            System.out.println("로그인 실패.");
            return "member/login";
        }
    }
    // 로그아웃
    @GetMapping("/member_logout")
    public String member_logout (HttpServletRequest request)  {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}



