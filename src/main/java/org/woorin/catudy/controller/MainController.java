package org.woorin.catudy.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.MemberService;
import org.woorin.catudy.service.RoomService;

@Controller
public class MainController {

	@Autowired
	private RoomService roomService;

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String indexPage(Model model) {
		System.out.println("HELLO");
		List<RoomDTO> roomList = roomService.room_list();
		model.addAttribute("roomList", roomList);
        return "index";
    }


    @PostMapping("/newsmoreroom")
    @ResponseBody
    public List<RoomDTO> getroom(int room_no) {
        List<RoomDTO> getroom = roomService.getroom(room_no);
        return getroom;
    }

    @PostMapping("/roomList")
    @ResponseBody
    public String roomList(@RequestParam("room_title")String room_title) {
        String roomTitle = roomService.roomList(room_title);
        return "roomTitle";
    }

    // 기능 확인을 위한 임시 페이지입니다. 서비스 시작전에 방번호(식별자)를 받아와 방을 생성해야 합니다.
    // @GetMapping("/show")
    // public String show() {
    //     System.out.println("HELLO SHOW");
    //     return "show/show";
    // }

    // 기능 확인을 위한 임시 페이지입니다.
    @GetMapping("/roomInfo")
    public String roomInfo(Model model) {
        RoomDTO aRoom =  roomService.getRoom(1);
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

        return "room/roomInfo";
    }

    // 기능 확인을 위한 임시 페이지입니다.
    @GetMapping(value="/roomInfo_test")
    public String getMethodName(Model model) {
        RoomDTO room = roomService.getRoom(1);
        model.addAttribute("room", room);
        return  "room/roomInfo";
    }
}
