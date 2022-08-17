package org.woorin.catudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String indexPage() {
		System.out.println("HELLO");
        return "index";
    }

    @GetMapping("/show")
    public String show() {
        System.out.println("HELLO SHOW");
        return "show/show";
    }


}
