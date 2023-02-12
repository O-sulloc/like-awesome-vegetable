package com.i5e2.likeawesomevegetable.user.basic.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.user.basic.dto.*;
import com.i5e2.likeawesomevegetable.user.basic.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("User Controller")
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 폼을 작성을 통해 서비스에 가입한다.")
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Result<UserJoinResponse>> join(@RequestBody @Valid UserJoinRequest dto) {
        UserJoinResponse userJoinResponse = userService.join(dto);
        return ResponseEntity.ok().body(Result.success(userJoinResponse));
    }

    /*
     * refactoring 사항 - ajax로 구현해보기
     * */
    /*@PostMapping("/join/check-email") // email을 사용할 수 있을 경우 true
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> targetEmail) {
        Boolean isEmailExist = userService.isNotEmailExist(targetEmail.get("email"));
        return ResponseEntity.ok().body(isEmailExist);
    }*/

    @ApiOperation(value = "회원 로그인", notes = "로그인 과정을 통해 서비스를 이용하기 위한 토큰을 발급받는다.")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Result<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest dto) {
        UserLoginResponse userLoginResponse = userService.login(dto);
        return ResponseEntity.ok().body(Result.success(userLoginResponse));
    }

    @ApiOperation(value = "회원 로그아웃", notes = "로그아웃으로 기존 토큰으로 서비스 접근을 막는다.")
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Result<UserLogoutResponse>> logout(Authentication authentication) {
        UserLogoutResponse userLogoutResponse = userService.logout(authentication);
        return ResponseEntity.ok().body(Result.success(userLogoutResponse));
    }
}
