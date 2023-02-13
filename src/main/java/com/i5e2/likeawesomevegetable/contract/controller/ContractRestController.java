package com.i5e2.likeawesomevegetable.contract.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.contract.service.ContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@RestController
@Api("Contract Controller")
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractRestController {

    private final ContractService contractService;

    // 경매-입찰
    @ApiOperation(value = "경매 전자 계약 진행 상황 조회", notes = "경매 계약서의 서명 진행 단계를 보여준다.")
    @GetMapping("/auction/{documentId}/status")
    public ResponseEntity<Result> getAuctionStatus(@PathVariable String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        log.info("컨트롤러 진입");

        String msg = contractService.getContractStatus(documentId);

        return ResponseEntity.ok().body(Result.success(msg));
    }

    // 모집-참여
    @ApiOperation(value = "모집 전자 계약 진행 상황 조회", notes = "모집 계약서의 서명 진행 단계를 보여준다.")
    @GetMapping("/buying/{documentId}/status")
    public ResponseEntity<Result> getBuyingStatus(@PathVariable String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        String msg = contractService.getContractStatus(documentId);

        return ResponseEntity.ok().body(Result.success(msg));
    }
}