package com.i5e2.likeawesomevegetable.mypage.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.mypage.dto.MypagePointEvenLogResponse;
import com.i5e2.likeawesomevegetable.mypage.service.MyPageApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("User MyPage Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageApiController {
    private final MyPageApiService myPageApiService;

    @ApiOperation(value = "로그인한 정회원의 포인트 내역 조회(최신순)"
            , notes = "사용자 인증 이메일을 통해 포인트 상세 내역을 조회한다.")
    @GetMapping("/user")
    public ResponseEntity<Result> readUserPointLog(Authentication authentication) {
        List<MypagePointEvenLogResponse> userPointEvenLogResponses = myPageApiService.readUserPointLogs(authentication.getName());
        return ResponseEntity.ok().body(Result.success(userPointEvenLogResponses));
    }

    @ApiOperation(value = "로그인한 관리자의 정산 내역 조회(최신순)"
            , notes = "관리자 인증 이메일을 통해 정산 상세 내역을 조회한다.")
    @GetMapping("/admin")
    public ResponseEntity<Result> readAdminTransferLog(Authentication authentication) {
        List<MypagePointEvenLogResponse> mypagePointEvenLogResponses = myPageApiService.readAdminTransferLogs(authentication.getName());
        return ResponseEntity.ok().body(Result.success(mypagePointEvenLogResponses));
    }

    @PostMapping("/verification")
    public ResponseEntity<Result> makeVerification(Authentication authentication) {
        Result result = myPageApiService.makeUserVerification(authentication.getName());
        return ResponseEntity.ok().body(result);
    }
}
