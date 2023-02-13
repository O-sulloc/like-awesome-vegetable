package com.i5e2.likeawesomevegetable.user.basic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api("User Login Page Controller")
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @ApiOperation(value = "로그인 페이지", notes = "로그인 창을 불러온다.")
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

}
