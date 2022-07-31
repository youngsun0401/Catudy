package org.woorin.catudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woorin.catudy.service.TestService;

@Controller
public class TestController {
	@Autowired private TestService testService;
	
	//// 주석주석
	@GetMapping("/")
	String get_testPage() {
		return "index";
	}
	
	@PostMapping("/")
	String post_testPage() {
		return "index";
	}
	
	@GetMapping("/test")
	String get_testtestPage( @RequestParam("param")String param, Model model, HttpSession session ) {
		session.setAttribute("login", "something");
		String login = session.getAttribute("login")+"";

		model.addAttribute("parameter", param);
		model.addAttribute("testSelect", testService.testMethod(1));
		
		return "testPage";
	}

}
