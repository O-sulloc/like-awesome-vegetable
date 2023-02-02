package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Response;
import com.i5e2.likeawesomevegetable.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Response<UserJoinResponse>> join(@RequestBody UserJoinRequest dto){
        UserJoinResponse userJoinResponse = userService.join(dto);
        return ResponseEntity.ok().body(Response.success(userJoinResponse));
    }

    /*
    * refactoring 사항 - ajax로 구현해보기
    * */
    @PostMapping("/join/check-email") // email을 사용할 수 있을 경우 true
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> targetEmail){
        Boolean isEmailExist = userService.isNotEmailExist(targetEmail.get("email"));
        return ResponseEntity.ok().body(isEmailExist);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Response<UserLoginResponse>> login(@RequestBody UserLoginRequest dto) {
        UserLoginResponse userLoginResponse = userService.login(dto);
        return ResponseEntity.ok().body(Response.success(userLoginResponse));
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Response<UserLogoutResponse>> logout(Authentication authentication) {
        UserLogoutResponse userLogoutResponse = userService.logout(authentication);
        return ResponseEntity.ok().body(Response.success(userLogoutResponse));
    }
}
