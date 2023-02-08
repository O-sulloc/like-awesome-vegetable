package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.MyPageApiService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypagePointEvenLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageApiController {

    private final MyPageApiService myPageApiService;

    @GetMapping("/user/{id}")
    public ResponseEntity<Result<List<MypagePointEvenLogResponse>>> readUserPointLog(@PathVariable("id") Long id) {
        List<MypagePointEvenLogResponse> userPointEvenLogResponses = myPageApiService.readUserPointLogs(id);
        return ResponseEntity.ok().body(Result.success(userPointEvenLogResponses));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Result<List<MypagePointEvenLogResponse>>> readAdminPointLog(@PathVariable("id") Long id) {
        List<MypagePointEvenLogResponse> mypagePointEvenLogResponses = myPageApiService.readAdminPointLogs(id);
        return ResponseEntity.ok().body(Result.success(mypagePointEvenLogResponses));
    }

}
