package com.i5e2.likeawesomevegetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping("/pending-deposit")
    public String mypagePendingPost() {
        return "user/mypage/mypage-deposit-pending-list";
    }
}
