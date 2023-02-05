package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.contract.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractRestController {

    private final ContractService contractService;

    // 계약 완료 후 계좌 정보 가져오기
    @GetMapping("test")
    public void getMail() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        HashMap<String, String> accountInfo = contractService.getContractInfo("404e20a5f8a741c5bd241ac0ab559aa7");

        log.info("info:{}", accountInfo.get("accountNo"));
        log.info("info:{}", accountInfo.get("accountName"));
        log.info("info:{}", accountInfo.get("accountOwnerName"));
    }
}
