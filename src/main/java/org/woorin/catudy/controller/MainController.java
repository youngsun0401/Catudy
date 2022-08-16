package org.woorin.catudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String test() {
		System.out.println("HELLO");
        return "index";
    }

    @GetMapping("/show")
    public String show() {
        System.out.println("HELLO SHOW");
        return "show/show";
    }


<<<<<<< HEAD
    @GetMapping("/join")
    public String join() {
        System.out.println("HELLO JOIN");
        return "user/join";
    }

    @GetMapping("/recruit")
    public String recruit() {
		System.out.println("HELLO");
        return "board/recruit";
    }

=======
>>>>>>> 99668939e9f456dc3b650b20559629bb4c7bc290
}
