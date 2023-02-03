package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationRequest;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationService;
import com.i5e2.likeawesomevegetable.domain.verification.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Result<SendVerifyEmailResponse>> sendVerifyEmail(@RequestBody SendEmailRequest sendEmailRequest) throws Exception {
        SendVerifyEmailResponse sendVerifyEmailResponse = verificationService.sendSimpleMessage(sendEmailRequest.getEmail());
        return ResponseEntity.ok().body(Result.success(sendVerifyEmailResponse));
    }

    /*     메일 검증     */
    @PostMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<Result<VerifyResponse>> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest,
                                                              Authentication authentication) {
        VerifyResponse verifyEmailResponse = verificationService.verifyEmail(verifyEmailRequest.getEmailCode(), authentication.getName());
        return ResponseEntity.ok().body(Result.success(verifyEmailResponse));
    }

    /*     사이트 작동 검증     */
    @PostMapping("/verify-url")
    public ResponseEntity<Result<VerifyResponse>> verifyUrl(@RequestBody VerifyUrlRequest urlRequest,
                                                            Authentication authentication) {
        VerifyResponse verifyUrlResponse = verificationService.verifyUrl(urlRequest.getUrl(), authentication.getName());
        return ResponseEntity.ok().body(Result.success(verifyUrlResponse));
    }

    /*     사업자 등록정보 검증     */
    @PostMapping("/verify-business-no")
    public ResponseEntity<Result<VerifyResponse>> verifyCompany(@RequestBody VerificationRequest verificationRequest,
                                                                Authentication authentication) throws IOException {
        VerifyResponse businessNoVerifyResponse = verificationService.verifyCompany(
                verificationRequest.getBusinessNo(),
                verificationRequest.getStartDate(),
                verificationRequest.getManagerName(),
                authentication.getName()
        );
        return ResponseEntity.ok().body(Result.success(businessNoVerifyResponse));
    }

    /*     기업 사용자 등록     */
    @PostMapping("/verify-company-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyCompanyUser(@RequestBody VerifyCompanyUserRequest verifyCompanyUserRequest,
                                                                        Authentication authentication) {
        VerifyUserResponse verifyCompanyUserResponse = verificationService.verifyCompanyUser(verifyCompanyUserRequest, authentication.getName());
        return ResponseEntity.ok().body(Result.success(verifyCompanyUserResponse));
    }

    /*     농가 사용자 등록     */
    @PostMapping("/verify-farm-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyFarmUser(@RequestBody VerifyFarmUserRequest verifyFarmUserRequest,
                                                                     Authentication authentication) {
        VerifyUserResponse verifyFarmUserResponse = verificationService.verifyFarmUser(verifyFarmUserRequest, authentication.getName());
        return ResponseEntity.ok().body(Result.success(verifyFarmUserResponse));
    }

}