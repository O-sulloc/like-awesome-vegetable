package com.i5e2.likeawesomevegetable.verification.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.verification.dto.*;
import com.i5e2.likeawesomevegetable.verification.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api("User Verification Controller")
@RequestMapping("/api/v1/user/mypage")
public class VerificationController {
    private final VerificationService verificationService;

    @ApiOperation(
            value = "검증 메일 전송",
            notes = "입력받은 이메일에 인증번호를 전송한다.")
    @PostMapping("/send-verify-email")
    @ResponseBody
    public ResponseEntity<Result<SendVerifyEmailResponse>> sendVerifyEmail(@RequestBody SendEmailRequest sendEmailRequest) throws Exception {
        Result<SendVerifyEmailResponse> sendVerifyEmailResponse = verificationService.sendSimpleMessage(sendEmailRequest.getEmail());
        return ResponseEntity.ok().body(sendVerifyEmailResponse);
    }

    @ApiOperation(
            value = "메일 검증",
            notes = "입력받은 이메일에 보낸 인증번호가 맞는지 검증한다.")
    @PostMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<Result<VerifyResponse>> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest,
                                                              Authentication authentication) {
        Result<VerifyResponse> verifyEmailResponse = verificationService.verifyEmail(verifyEmailRequest.getKeyEmail(), verifyEmailRequest.getEmailCode(), authentication.getName());
        return ResponseEntity.ok().body(verifyEmailResponse);
    }

    @ApiOperation(
            value = "회원 사이트 검증",
            notes = "입력받은 URL이 정상 작동하는지 검증한다.")
    @PostMapping("/verify-url")
    public ResponseEntity<Result<VerifyResponse>> verifyUrl(@RequestBody VerifyUrlRequest urlRequest,
                                                            Authentication authentication) {
        Result<VerifyResponse> verifyUrlResponse = verificationService.verifyUrl(urlRequest.getUrl(), authentication.getName());
        return ResponseEntity.ok().body(verifyUrlResponse);
    }

    @ApiOperation(
            value = "사업자 등록정보 검증",
            notes = "사업자 등록정보 확인 API를 통해 실제 등록된 사업자인지 검증한다.")
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

    @ApiOperation(
            value = "기업 사용자 등록",
            notes = "검증이 완료된 사용자를 기업 사용자로 등록한다.")
    @PostMapping("/verify-company-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyCompanyUser(@RequestBody VerifyCompanyUserRequest verifyCompanyUserRequest,
                                                                        Authentication authentication) {
        Result<VerifyUserResponse> verifyCompanyUserResponse = verificationService.verifyCompanyUser(verifyCompanyUserRequest, authentication.getName());
        return ResponseEntity.ok().body(verifyCompanyUserResponse);
    }

    @ApiOperation(
            value = "농가 사용자 등록",
            notes = "검증이 완료된 사용자를 농가 사용자로 등록한다.")
    @PostMapping("/verify-farm-user")
    public ResponseEntity<Result<VerifyUserResponse>> verifyFarmUser(@RequestBody VerifyFarmUserRequest verifyFarmUserRequest,
                                                                     Authentication authentication) {
        Result<VerifyUserResponse> verifyFarmUserResponse = verificationService.verifyFarmUser(verifyFarmUserRequest, authentication.getName());
        return ResponseEntity.ok().body(verifyFarmUserResponse);
    }

}