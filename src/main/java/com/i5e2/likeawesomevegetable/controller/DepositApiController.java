package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.deposit.DepositService;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("User Deposit Pending Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DepositApiController {
    private final DepositService depositService;

    @ApiOperation(value = "사용자 보증금 Pending 요청"
            , notes = "보증금 예치 가능여부 체크 후 보증금 데이터 저장, 사용자 포인트/게시글 활성화 상태를 업데이트 한다.")
    @PostMapping("/deposit-pending")
    public ResponseEntity<Result> addUserPendingDeposit(@RequestBody @Valid DepositPendingRequest depositPendingRequest, Authentication authentication) {
        //TODO: 예치금 전용 계좌로 자동이체 (자동 결제의 경우 API 유료, 수동 자동이체로 진행)
        Result<DepositPendingResponse> depositPendingResponse = depositService.addUserPendingDeposit(depositPendingRequest);
        return ResponseEntity.ok().body(depositPendingResponse);
    }
}
