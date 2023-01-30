package com.i5e2.likeawesomevegetable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.i5e2.likeawesomevegetable.domain.apply.SmsService;
import com.i5e2.likeawesomevegetable.domain.apply.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.MessageRequest;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
@RestController
@RequestMapping("/market/buying/{buyingId}")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    // 세션 키로 사용될 문자열
    public static class SessionConst {
        public static final String SMS_AUTH = "sms";
    }

    // 인증번호 발송
    @PostMapping("/sms-send")
    public ResponseEntity<String> sendSms(@RequestBody MessageRequest request, Authentication authentication)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        smsService.sendSms(request, authentication.getName());
        return ResponseEntity.ok("인증번호가 발송되었습니다."); //TODO: 리턴 방식 리펙토링
    }

    // 인증번호 검증
    @PostMapping("/sms-confirm")
    public ResponseEntity<String> verification(@RequestBody InfoRequest request, Authentication authentication,
                                               HttpServletRequest httpServletRequest) {

        User user = smsService.verifySms(request, authentication.getName());

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConst.SMS_AUTH, user);

        return ResponseEntity.ok("인증이 완료되었습니다."); //TODO: 리턴 방식 리펙토링
    }
}