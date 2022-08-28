package org.woorin.catudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.AttendDTO;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.ChattingService;
import org.woorin.catudy.service.MemberService;
import org.woorin.catudy.service.RoomService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private ChattingService chattigService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MainMapper mapper;

    // 스터디만드는 page 이동
    @GetMapping("/createRoom")
    public String createRoom() {
        return "room/createRoom";
    }

    // 스터디생성
    @PostMapping("/room_insert")
    public String room_insert(RoomDTO dto, boolean room_open, HttpSession session, AttendDTO ato) {
        System.out.println("====================================");
        dto.setRoom_ruler(loginId(session));
        dto.setRoom_open(room_open);
        System.out.println(dto);
        ato.setAttend_target_member(loginId(session));
        roomService.room_insert(dto, ato);
        System.out.println(ato);
        return "redirect:/";
    }



    
	//// 스터디방 입장
	@GetMapping("/show")
	public String show(@RequestParam int room, Model model, HttpSession session) {
        RoomDTO dto = roomService.getRoom(room);
        //// 비로그인이면 로그인하러 가라고 하기
        if( loginId(session) == 0 ){
            return "redirect:/login";
        }
        //// 채팅방 대기열에 추가
        String chattingPassword = "abc";
        chattigService.newWaiting(
        		room, 
        		loginId(session), 
        		chattingPassword, 
        		memberService.member_nick(loginId(session)));
		//// TODO 미구현   자기가 속한 스터디방이 아니면 입장 거부됨
        model.addAttribute("member_no", loginId(session));
        model.addAttribute("room_no", room);
        model.addAttribute("chatting_password", chattingPassword);
        model.addAttribute("dto", dto);
        return "show/show";
	}

    // 회원번호 가져오기
    private static int loginId( HttpSession session ) {
        if( session.getAttribute("member_no") == null ) return 0;
        return Integer.parseInt(
                session.getAttribute("member_no")+""
        );
    }




}
