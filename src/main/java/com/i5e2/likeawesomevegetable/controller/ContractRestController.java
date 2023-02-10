package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.contract.ContractService;
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
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractRestController {

    private final ContractService contractService;

    // 경매 계약서 생성
    @GetMapping("/{auctionId}/{biddingId}/new-auction-contract")
    public void newAuctionForm(@PathVariable Long auctionId, @PathVariable Long biddingId) {

    }

    // 경매-입찰
    @GetMapping("/auction/{documentId}/status")
    public ResponseEntity<Result> getAuctionStatus(@PathVariable String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        log.info("컨트롤러 진입");

        String msg = contractService.getContractStatus(documentId);

        return ResponseEntity.ok().body(Result.success(msg));
    }

    // 모집-참여
    @GetMapping("/buying/{documentId}/status")
    public ResponseEntity<Result> getBuyingStatus(@PathVariable String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        String msg = contractService.getContractStatus(documentId);

        return ResponseEntity.ok().body(Result.success(msg));
    }
}