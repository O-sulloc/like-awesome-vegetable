package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.deposit.DepositService;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DepositApiController {
    private final DepositService depositService;

    @PostMapping("/deposit-pending")
    public ResponseEntity<Result<DepositPendingResponse>> addUserPendingDeposit(@RequestBody DepositPendingRequest depositPendingRequest) {
        //TODO: 예치금 전용 계좌로 자동이체
        Result<DepositPendingResponse> depositPendingResponse = depositService.addUserPendingDeposit(depositPendingRequest);
        //TODO: 모집 게시글 활성화 여부 업데이트
        return ResponseEntity.ok().body(depositPendingResponse);
    }

}
