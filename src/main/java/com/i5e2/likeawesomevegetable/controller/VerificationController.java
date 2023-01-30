package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.user.UserType;
import com.i5e2.likeawesomevegetable.domain.verification.Verification;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationRequest;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationService;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/mypage")
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/send-verify-email")
    public ResponseEntity<Verification> sendVerifyEmail() {
        
    }

    @PostMapping("/verify-url")
    public ResponseEntity<Verification> verifyUrl(@RequestBody VerificationUrl verificationUrl) {
        boolean urlCheckResult = verificationService.verifyUrl(verificationUrl.getUrl());
        if (urlCheckResult) {
            return ResponseEntity.ok().body(Verification.VERIFIED);        // TODO: 리턴 타입 변경
        }
        return ResponseEntity.ok().body(Verification.NOT_VERIFIED);
    }

    @PostMapping("/verify-business-no")
    public UserType verifyCompany(@RequestBody VerificationRequest verificationRequest) throws IOException {
        return verificationService.verifyCompany(
                verificationRequest.getBusinessNo(),
                verificationRequest.getStartDate(),
                verificationRequest.getManagerName()
        );
    }

}
