package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
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
    // TODO: Service 단에서 Result<T>에 담아서 Return
    private final VerificationService verificationService;

    /*     검증 메일 전송     */
    @PostMapping("/send-verify-email")
    @ResponseBody
    public ResponseEntity<Result<SendVerifyEmailResponse>> sendVerifyEmail(@RequestBody SendEmailRequest sendEmailRequest) throws Exception {
        Result<SendVerifyEmailResponse> sendVerifyEmailResponse = verificationService.sendSimpleMessage(sendEmailRequest.getEmail());
        return ResponseEntity.ok().body(sendVerifyEmailResponse);
    }

    /*     메일 검증     */
    @PostMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<Result<VerifyResponse>> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest,
                                                              Authentication authentication) {
        Result<VerifyResponse> verifyEmailResponse = verificationService.verifyEmail(verifyEmailRequest.getKeyEmail(), verifyEmailRequest.getEmailCode(), authentication.getName());
        return ResponseEntity.ok().body(verifyEmailResponse);
    }

    /*     사이트 작동 검증     */
    @PostMapping("/verify-url")
    public ResponseEntity<Result<VerifyResponse>> verifyUrl(@RequestBody VerifyUrlRequest urlRequest,
                                                            Authentication authentication) {
        Result<VerifyResponse> verifyUrlResponse = verificationService.verifyUrl(urlRequest.getUrl(), authentication.getName());
        return ResponseEntity.ok().body(verifyUrlResponse);
    }

    /*     사업자 등록정보 검증     */
    @PostMapping("/verify-business-no")
    public ResponseEntity<Result<VerifyResponse>> verifyCompany(@RequestBody VerificationRequest verificationRequest,
                                                                Authentication authentication) throws IOException {
        Result<VerifyResponse> businessNoVerifyResponse = verificationService.verifyCompany(
                verificationRequest.getBusinessNo(),
                verificationRequest.getStartDate(),
                verificationRequest.getManagerName(),
                authentication.getName()
        );
        return ResponseEntity.ok().body(businessNoVerifyResponse);
    }

    /*     기업 사용자 등록     */
    @PostMapping("/verify-company-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyCompanyUser(@RequestBody VerifyCompanyUserRequest verifyCompanyUserRequest,
                                                                        Authentication authentication) {
        Result<VerifyUserResponse> verifyCompanyUserResponse = verificationService.verifyCompanyUser(verifyCompanyUserRequest, authentication.getName());
        return ResponseEntity.ok().body(verifyCompanyUserResponse);
    }

    /*     농가 사용자 등록     */
    @PostMapping("/verify-farm-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyFarmUser(@RequestBody VerifyFarmUserRequest verifyFarmUserRequest,
                                                                     Authentication authentication) {
        Result<VerifyUserResponse> verifyFarmUserResponse = verificationService.verifyFarmUser(verifyFarmUserRequest, authentication.getName());
        return ResponseEntity.ok().body(verifyFarmUserResponse);
    }

}