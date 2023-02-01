package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.verification.VerificationRequest;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationService;
import com.i5e2.likeawesomevegetable.domain.verification.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/mypage")
public class VerificationController {
    // TODO: return Verification enum으로 변경, response에 담아서 전달
    private final VerificationService verificationService;

    /*     검증 메일 전송     */
    @PostMapping("/send-verify-email")
    @ResponseBody
    public ResponseEntity<SendVerifyEmailResponse> sendVerifyEmail(@RequestBody SendEmailCodeRequest sendEmailCodeRequest) throws Exception {
        SendVerifyEmailResponse sendVerifyEmailResponse = verificationService.sendSimpleMessage(sendEmailCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendVerifyEmailResponse);
    }

    /*     메일 검증     */
    @PostMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<VerifyResponse> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        VerifyResponse verifyEmailResponse = verificationService.verifyEmail(verifyEmailRequest.getEmailCode());
        return ResponseEntity.ok().body(verifyEmailResponse);
    }

    /*     사이트 작동 검증     */
    @PostMapping("/verify-url")
    public ResponseEntity<VerifyResponse> verifyUrl(@RequestBody VerifyUrlRequest urlRequest) {
        VerifyResponse verifyUrlResponse = verificationService.verifyUrl(urlRequest.getUrl());
        return ResponseEntity.ok().body(verifyUrlResponse);
    }

    /*     사업자 등록정보 검증     */
    @PostMapping("/verify-business-no")
    public ResponseEntity<VerifyResponse> verifyCompany(@RequestBody VerificationRequest verificationRequest) throws IOException {
        VerifyResponse businessNoVerifyResponse = verificationService.verifyCompany(
                verificationRequest.getBusinessNo(),
                verificationRequest.getStartDate(),
                verificationRequest.getManagerName());
        return ResponseEntity.ok().body(businessNoVerifyResponse);
    }
}
