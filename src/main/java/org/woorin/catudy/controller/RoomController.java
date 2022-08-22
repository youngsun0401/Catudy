package org.woorin.catudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.RoomService;

import javax.servlet.http.HttpSession;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private MainMapper mapper;

    // 스터디만드는 page 이동
    @GetMapping("/createRoom")
    public String createRoom() {
        return "room/createRoom";
    }

    // 스터디생성
    @PostMapping("/room_insert")
    public String room_insert(RoomDTO dto, boolean room_open, HttpSession session) {
        System.out.println("====================================");
        dto.setRoom_ruler(loginId(session));
        dto.setRoom_open(room_open);
        System.out.println(dto);
        roomService.room_insert(dto);
        return "redirect:/";
    }

	//// 스터디방 입장
	@GetMapping("/show")
	public String show(@RequestParam Integer room, Model model, HttpSession session) {
		//// ??? 미구현   자기가 속한 스터디방이 아니면 입장 거부됨
		roomService.enterRoom( room, loginId(session));
        model.addAttribute("member_no", loginId(session));
        model.addAttribute("room_no", room);
		return "show/show";
	}



    // 회원번호 가져오기
    private static int loginId( HttpSession session ) {
        if( session.getAttribute("member_no") == null ) return 0;
        return Integer.parseInt( session.getAttribute("member_no")+"" );
    }

}
