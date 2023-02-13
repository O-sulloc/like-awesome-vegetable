package com.i5e2.likeawesomevegetable.contract.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Configuration
@Slf4j
@Getter
public class ContractSignature {

    private String signature; // 서명
    private String executionTime; // 만료일
    private String encodedKey; // base64 encode

    // 1. 서명(signature), execution_time 발급
    public String getSignature() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        //private key
        String privateKeyHexStr = "3041020100301306072a8648ce3d020106082a8648ce3d0301070427302502010104205af3aa36270da06ee22007d9bd81113794c935ebc8f8ef630bcb7a7f9e999c2f";
        KeyFactory keyFact = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec psks8KeySpec = new PKCS8EncodedKeySpec(new BigInteger(privateKeyHexStr, 16).toByteArray());
        PrivateKey privateKey = keyFact.generatePrivate(psks8KeySpec);

        //execution_time - 서버 현재 시간
        long executionTime = new Date().getTime();
        String executionTimeStr = String.valueOf(executionTime);

        //eformsign_signature 생성
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initSign(privateKey);
        ecdsa.update(executionTimeStr.getBytes("UTF-8"));
        String eformsign_signature = new BigInteger(ecdsa.sign()).toString(16);

        //현재 시간 및 현재 시간 서명값
        log.info("execution_time:{}", executionTime);
        log.info("eformsign_signature:{}", eformsign_signature);

        this.signature = eformsign_signature;
        this.executionTime = String.valueOf(executionTime);

        return signature;
    }

    // 2. authorize (base64로 인코딩한 값. Bearer 토큰으로 사용)
    public String getEncodedKey() {
        String apiKey = "7a1b6896-8531-4e29-830c-f2194a101a08";
        String encodedKey = Base64.getEncoder().encodeToString(apiKey.getBytes());

        log.info("encodedKey:{}", encodedKey);

        this.encodedKey = encodedKey;
        return encodedKey;
    }

}