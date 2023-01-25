package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.user.UserType;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationRequest;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationService;
import com.i5e2.likeawesomevegetable.domain.verification.VerificationUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verifying-test")
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping
    public ResponseEntity<String> verifyUrl(@RequestBody VerificationUrl verificationUrl) {
        boolean urlCheckResult = verificationService.verifyUrl(verificationUrl.getUrl());
        if (urlCheckResult) {
            return ResponseEntity.ok().body("검증완료");        // TODO: 리턴 타입 변경
        }
        return ResponseEntity.ok().body("유효하지 않은 url");
    }

    @PostMapping
    public UserType verifyCompany(@RequestBody VerificationRequest verificationRequest) throws IOException {
        return verificationService.verifyCompany(
                verificationRequest.getBusinessNo(),
                verificationRequest.getStartDate(),
                verificationRequest.getManagerName()
        );
    }

}
