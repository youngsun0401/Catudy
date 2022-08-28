package org.woorin.catudy.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.AttendDTO;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.ChattingService;
import org.woorin.catudy.service.MemberService;
import org.woorin.catudy.service.RoomService;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ChattingService chattigService;
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
	public String show(@RequestParam("id") Integer room, Model model, HttpSession session) {
        //// 비로그인이면 로그인하러 가라고 하기
        if( loginId(session) == 0 ){
            return "redirect:/login";
        }
        
        RoomDTO aRoom = roomService.getRoom(room);
        model.addAttribute("room", aRoom);

        // 방장이름 가져오기
        String host = memberService.member_find(aRoom.getRoom_ruler()).getMember_nick();
        model.addAttribute("host", host);

        // 팀원들 정보 가져오기: 서비스에서 방 번호를 기반으로 attendTBL 테이블에서 정보를 가져옵니다.
        Iterator<MemberDTO> members = memberService.member_list_on_a_room(aRoom.getRoom_no()).iterator();
        ArrayList<String> member_nicks = new ArrayList<String>();
        while(members.hasNext()){
            member_nicks.add(members.next().getMember_nick());
        }
        model.addAttribute("nicks", member_nicks);
        
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
        model.addAttribute("chatting_password", "abc");

        model.addAttribute("chatting_password", chattingPassword);
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
