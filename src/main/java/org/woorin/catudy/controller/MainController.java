package org.woorin.catudy.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.RoomService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String indexPage(Model model) {
		System.out.println("HELLO");
        List<RoomDTO> roomList = roomService.room_list();
        model.addAttribute("roomList", roomList);
        return "index";
    }


    @GetMapping("/show")
    public String show() {
        System.out.println("HELLO SHOW");
        return "show/show";
    }

}
