package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.alarm.ALarmService;
import com.i5e2.likeawesomevegetable.domain.alarm.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmRestController {

    private final ALarmService aLarmService;
    @GetMapping("")
    public ResponseEntity<Result<List<AlarmResponse>>> getAlarms(Authentication authentication) {
        String email = authentication.getName();
        List<AlarmResponse> alarms = aLarmService.getAlarms(email);
        return ResponseEntity.ok().body(Result.success(alarms));
    }
}
