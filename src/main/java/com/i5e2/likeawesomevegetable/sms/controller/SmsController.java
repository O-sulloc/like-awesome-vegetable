package com.i5e2.likeawesomevegetable.sms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.sms.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.sms.dto.MessageRequest;
import com.i5e2.likeawesomevegetable.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("SmsController")
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    // 모집 참여 인증번호 발송
    @ApiOperation(value = "모집 참여 인증번호 발송",
            notes = "모집에 참여하기 전 본인인증을 위한 인증번호를 문자로 발송한다.")
    @PostMapping("/buying/{companyBuyingId}")
    public ResponseEntity<Result<String>> applySms(@RequestBody MessageRequest request, @PathVariable Long companyBuyingId, Authentication authentication)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        smsService.applySms(request, companyBuyingId, authentication.getName());
        return ResponseEntity.ok(Result.success("인증번호가 발송되었습니다."));
    }

    // 입찰 인증번호 발송
    @ApiOperation(value = "경매 입찰 인증번호 발송",
            notes = "경매에 입찰하기 전 본인인증을 위한 인증번호를 문자로 발송한다.")
    @PostMapping("/auction/{farmAuctionId}")
    public ResponseEntity<Result<String>> auctionSms(@RequestBody MessageRequest request, @PathVariable Long farmAuctionId, Authentication authentication)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        smsService.auctionSms(request, farmAuctionId, authentication.getName());
        return ResponseEntity.ok(Result.success("인증번호가 발송되었습니다."));
    }

    // 인증번호 검증
    @ApiOperation(value = "인증번호 검증",
            notes = "문자로 발송된 인증번호와 입력받은 인증번호를 통해 검증하여 성공하면 세션을 값을 저장한다.")
    @PostMapping("/confirm")
    public ResponseEntity<Result<String>> verification(@RequestBody InfoRequest request, Authentication authentication, HttpSession session) {

        smsService.verifySms(request, authentication.getName(), session);
        return ResponseEntity.ok(Result.success("인증이 완료되었습니다."));
    }
}