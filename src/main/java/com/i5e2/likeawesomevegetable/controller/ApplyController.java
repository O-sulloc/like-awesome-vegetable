package com.i5e2.likeawesomevegetable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.i5e2.likeawesomevegetable.domain.apply.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.MessageRequest;
import com.i5e2.likeawesomevegetable.domain.apply.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @GetMapping()
    public String getSmsPage() {
        return "";
    }

    // 인증번호 발송
    @PostMapping("/sms/send")
    public ResponseEntity<String> sendSms(@RequestBody MessageRequest request) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        applyService.sendSms(request);
        return ResponseEntity.ok("인증번호가 발송되었습니다."); //TODO: 리턴 방식 리펙토링
    }

    // 인증번호 검증
    @PostMapping("/sms/confirm")
    public ResponseEntity<String> verification(@RequestBody InfoRequest request) {

        applyService.verifySms(request);
        return ResponseEntity.ok("인증이 완료되었습니다."); //TODO: 리턴 방식 리펙토링
    }
}
