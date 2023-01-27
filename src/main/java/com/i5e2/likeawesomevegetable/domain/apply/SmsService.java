package com.i5e2.likeawesomevegetable.domain.apply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i5e2.likeawesomevegetable.domain.apply.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.MessageRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.SmsRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.SmsResponse;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ErrorCode;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SmsService {

    private final RedisSmsUtil redisSmsUtil;
    private final UserJpaRepository userJpaRepository;

    @Value("${sens.serviceId}")
    private String serviceId;

    @Value("${sens.accessKey}")
    private String accessKey;

    @Value("${sens.secretKey}")
    private String secretKey;

    @Value("${sens.senderPhone}")
    private String senderPhone;

    // 인증번호 발송
    public void sendSms(MessageRequest request, String userEmail) throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException, JsonProcessingException, URISyntaxException {

        // 휴대폰 번호 검증
        userJpaRepository.findByEmail(userEmail).filter(users -> Objects.equals(users.getPhoneNo(), request.getTo()))
                .orElseThrow(() -> new ApplyException(ErrorCode.PHONE_DISCORD, ErrorCode.PHONE_DISCORD.getMessage()));

        Long time = System.currentTimeMillis();
        String smsCode = mackCode();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        List<MessageRequest> messages = new ArrayList<>();
        messages.add(request);

        SmsRequest smsRequest = SmsRequest.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(senderPhone)
                .content("[멋쟁이 채소처럼] 인증번호 [" + smsCode + "]를 입력해주세요.")
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(smsRequest);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId +
                "/messages"), httpBody, SmsResponse.class);

        redisSmsUtil.saveSmsAuth(request.getTo(), smsCode);
    }

    // Body를 AccessKey Id와 맵핑되는 SecretKey로 암호화
    private String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));

        return Base64.encodeBase64String(rawHmac);
    }

    // 6자리 난수 생성
    private String mackCode() {

        StringBuffer key = new StringBuffer();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            key.append(rand.nextInt(10));
        }

        return key.toString();
    }

    // 인증번호 검증
    public void verifySms(InfoRequest request) {

        if (!isVerify(request)) {
            throw new ApplyException(ErrorCode.AUTHENTICATION_FAILED, ErrorCode.AUTHENTICATION_FAILED.getMessage());
        }
        redisSmsUtil.deleteSmsAuth(request.getPhone());
    }

    // 인증번호 일치 여부
    private boolean isVerify(InfoRequest request) {

        return (redisSmsUtil.hasKey(request.getPhone()) &&
                redisSmsUtil.getSmsAuth(request.getPhone()).equals(request.getCode()));
    }
}