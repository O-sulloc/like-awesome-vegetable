package com.i5e2.likeawesomevegetable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.apply.SmsService;
import com.i5e2.likeawesomevegetable.domain.apply.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    // 모집 참여 인증번호 발송
    @PostMapping("/buying/{companyBuyingId}")
    public ResponseEntity<Result<String>> applySms(@RequestBody MessageRequest request, @PathVariable Long companyBuyingId, Authentication authentication)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        smsService.applySms(request, companyBuyingId, authentication.getName());
        return ResponseEntity.ok(Result.success("인증번호가 발송되었습니다."));
    }

    // 입찰 인증번호 발송
    @PostMapping("/auction/{farmAuctionId}")
    public ResponseEntity<Result<String>> auctionSms(@RequestBody MessageRequest request, @PathVariable Long farmAuctionId, Authentication authentication)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        smsService.auctionSms(request, farmAuctionId, authentication.getName());
        return ResponseEntity.ok(Result.success("인증번호가 발송되었습니다."));
    }

    // 인증번호 검증
    @PostMapping("/confirm")
    public ResponseEntity<Result<String>> verification(@RequestBody InfoRequest request, Authentication authentication, HttpSession session) {

        smsService.verifySms(request, authentication.getName(), session);
        return ResponseEntity.ok(Result.success("인증이 완료되었습니다."));
    }
}