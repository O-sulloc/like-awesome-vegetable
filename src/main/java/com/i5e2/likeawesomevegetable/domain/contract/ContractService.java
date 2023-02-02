package com.i5e2.likeawesomevegetable.domain.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class ContractService {

    private final ContractSignature contractSignature;
    private String accessToken;
    private String refreshToken;
    private String companyId;

    public String getAccessToken() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException, ParseException {

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 셋팅
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("eformsign_signature", contractSignature.getSignature());
        httpHeaders.setBearerAuth(contractSignature.getEncodedKey());

        // request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("execution_time", contractSignature.getExecutionTime());
        body.add("member_id", "bujjaf@gmail.com");
        // 변수명 일치해야함

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        String url = "https://api.eformsign.com/v2.0/api_auth/access_token";
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        JSONParser parser = new JSONParser();
        JSONObject apiResponse = (JSONObject) parser.parse(response.getBody());
        JSONObject apiInfo = (JSONObject) apiResponse.get("api_key");
        JSONObject companyInfo = (JSONObject) apiInfo.get("company");
        JSONObject oauthInfo = (JSONObject) apiResponse.get("oauth_token");

        companyId = (String) companyInfo.get("company_id");
        accessToken = (String) oauthInfo.get("access_token");
        refreshToken = (String) oauthInfo.get("refresh_token");

        log.info("access_Token:{}", accessToken);
        log.info("refresh_token:{}", refreshToken);

        return accessToken;
    }

    // 2. 새 문서 작성 (최초 작성자가 외부자) - 주체:기업 (모집 완료 후 계약서 서명하여 농가에 전송)
    public void companySign() throws ParseException {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 셋팅
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(contractSignature.getEncodedKey());
        httpHeaders.set("company_id", companyId);
        httpHeaders.set("template_id", "7127d80aff1b4c048a25cfb2af0ef225");

        // request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        String url = "https://kr-api.eformsign.com/v2.0/api/documents/external?company_id=5a74ada340a1404c85cd23e643c8c3a5&template_id=7127d80aff1b4c048a25cfb2af0ef225";
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);


    }

    // 3. 주체: 농가 (기업이 보낸 계약서 농가가 마저 서명)

    // 4. 주체: 멋채 (기업/농가 서명 완료후, 멋채가 서명하여 공증)
    public void weSign() {}
}
