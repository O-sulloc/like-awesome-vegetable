package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.MyPageApiService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypagePointEvenLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageApiController {
    private final MyPageApiService myPageApiService;

    @GetMapping("/user")
    public ResponseEntity<Result> readUserPointLog(Authentication authentication) {
        List<MypagePointEvenLogResponse> userPointEvenLogResponses = myPageApiService.readUserPointLogs(authentication.getName());
        return ResponseEntity.ok().body(Result.success(userPointEvenLogResponses));
    }

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
