package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest dto){
        UserJoinResponse userJoinResponse = userService.join(dto);
        return ResponseEntity.ok().body(userJoinResponse);
    }

    @PostMapping("/join/check-email") // email을 사용할 수 있을 경우 true
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> targetEmail){
        Boolean isEmailExist = userService.isNotEmailExist(targetEmail.get("email"));
        return ResponseEntity.ok().body(isEmailExist);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest dto) {
        UserLoginResponse userLoginResponse = userService.login(dto);
        return ResponseEntity.ok().body(userLoginResponse);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<UserLogoutResponse> logout(Authentication authentication) {
        UserLogoutResponse userLogoutResponse = userService.logout(authentication);
        return ResponseEntity.ok().body(userLogoutResponse);
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }
}
