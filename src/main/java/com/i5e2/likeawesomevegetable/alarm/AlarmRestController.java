package com.i5e2.likeawesomevegetable.alarm;

import com.i5e2.likeawesomevegetable.alarm.dto.AlarmResponse;
import com.i5e2.likeawesomevegetable.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("User Alarm Controller")
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmRestController {

    private final ALarmService aLarmService;

    @ApiOperation(value = "사용자 알림 조회", notes = "사용자에게 도착한 알림을 리스트로 보여준다.")
    @GetMapping("")
    public ResponseEntity<Result<List<AlarmResponse>>> getAlarms(Authentication authentication) {
        String email = authentication.getName();
        List<AlarmResponse> alarms = aLarmService.getAlarms(email);
        return ResponseEntity.ok().body(Result.success(alarms));
    }
}
