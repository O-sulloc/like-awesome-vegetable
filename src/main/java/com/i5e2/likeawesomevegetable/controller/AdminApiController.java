package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.admin.AdminApiService;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminJoinRequest;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminLoginResponse;
import com.i5e2.likeawesomevegetable.domain.user.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final AdminApiService adminApiService;

    @PostMapping("/join")
    public ResponseEntity<Result> join(@RequestBody @Valid AdminJoinRequest adminJoinRequest) {
        Result adminJoin = adminApiService.join(adminJoinRequest);
        return ResponseEntity.ok().body(Result.success(adminJoin));
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        Result<AdminLoginResponse> adminLogin = adminApiService.login(userLoginRequest);
        return ResponseEntity.ok().body(adminLogin);
    }
}
