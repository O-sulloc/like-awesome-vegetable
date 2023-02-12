package com.i5e2.likeawesomevegetable.user.admin.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminJoinRequest;
import com.i5e2.likeawesomevegetable.user.admin.service.AdminApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Admin Sign-up Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final AdminApiService adminApiService;

    @ApiOperation(value = "관리자 회원가입"
            , notes = "관리자 회원가입 정보를 받아 관리자, 사용자 테이블에 저장한다.")
    @PostMapping("/join")
    public ResponseEntity<Result> join(@RequestBody @Valid AdminJoinRequest adminJoinRequest) {
        Result adminJoin = adminApiService.join(adminJoinRequest);
        return ResponseEntity.ok().body(Result.success(adminJoin));
    }

    /*@PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        Result<AdminLoginResponse> adminLogin = adminApiService.login(userLoginRequest);
        return ResponseEntity.ok().body(adminLogin);
    }*/
}
