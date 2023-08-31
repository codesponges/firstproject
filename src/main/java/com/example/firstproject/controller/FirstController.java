package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi") // 컨테스트 루트는 localhost:8080
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "효남");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "정윤찬");
        return "goodbye";
    }


}
