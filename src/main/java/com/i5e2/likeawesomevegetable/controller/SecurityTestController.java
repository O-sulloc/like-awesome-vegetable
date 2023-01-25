package com.i5e2.likeawesomevegetable.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {
    @GetMapping("/api/test-security")
    public ResponseEntity<String> securityChecking(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok().body(email);
    }

    @GetMapping("/api/test-role")
    public ResponseEntity<String> roleChecking(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok().body(email);
    }
}
