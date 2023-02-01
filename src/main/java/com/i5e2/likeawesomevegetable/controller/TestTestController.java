package com.i5e2.likeawesomevegetable.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TestTestController {

    @GetMapping("/test-security-ad")
    public ResponseEntity<String> securityChecking(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok().body(email);
    }

    @GetMapping("/test-security-ba")
    public ResponseEntity<String> securityCheckingB(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok().body(email);
    }

}
