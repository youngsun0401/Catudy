package org.woorin.catudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class webRTCController {
    
    @GetMapping("rtc")
    String rtcInit(){
        return "webRTC";
    }

}
