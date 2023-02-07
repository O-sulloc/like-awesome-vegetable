package com.i5e2.likeawesomevegetable.domain.apply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i5e2.likeawesomevegetable.domain.apply.dto.InfoRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.MessageRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.SmsRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.SmsResponse;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
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
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SmsService {

    private final RedisSmsUtil redisSmsUtil;
    private final UserJpaRepository userJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final FarmUserRepository farmUserRepository;
    private final String SMS_USER_ID = "SMS_USER_ID";

    @Value("${sens.serviceId}")
    private String serviceId;

    @Value("${sens.accessKey}")
    private String accessKey;

    @Value("${sens.secretKey}")
    private String secretKey;

    @Value("${sens.senderPhone}")
    private String senderPhone;

    // 참여 권한 확인 후 문자 발송
    public void applySms(MessageRequest request, Long companyBuyingId, String userEmail)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        // 휴대폰 번호 확인
        User user = userJpaRepository.findByEmail(userEmail).filter(users -> Objects.equals(users.getManaverPhoneNo(), request.getTo()))
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.PHONE_DISCORD, ApplyErrorCode.PHONE_DISCORD.getMessage()));

        // 신청자가 농가 사용자인지 확인
        Optional<FarmUser> farmUser = Optional.ofNullable(user.getFarmUser());

        if (farmUser.isEmpty()) {
            throw new ApplyException(ApplyErrorCode.NOT_FARM_USER, ApplyErrorCode.NOT_FARM_USER.getMessage());
        }

        // 모집 게시글이 있는지 확인
        companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.POST_NOT_FOUND, ApplyErrorCode.POST_NOT_FOUND.getMessage()));

        sendSms(request);
    }

    // 입찰 권한 확인 후 문자 발송
    public void auctionSms(MessageRequest request, Long farmAuctionId, String userEmail)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        // 휴대폰 번호 확인
        User user = userJpaRepository.findByEmail(userEmail).filter(users -> Objects.equals(users.getManaverPhoneNo(), request.getTo()))
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.PHONE_DISCORD, ApplyErrorCode.PHONE_DISCORD.getMessage()));

        // 신청자가 기업 사용자인지 확인
        Optional<CompanyUser> companyUser = Optional.ofNullable(user.getCompanyUser());

        if (companyUser.isEmpty()) {
            throw new ApplyException(ApplyErrorCode.NOT_COMPANY_USER, ApplyErrorCode.NOT_FARM_USER.getMessage());
        }

        // 경매 게시글이 있는지 확인
        farmUserRepository.findById(farmAuctionId)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.POST_NOT_FOUND, ApplyErrorCode.POST_NOT_FOUND.getMessage()));

        sendSms(request);
    }

    // 인증번호 발송
    public void sendSms(MessageRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException,
            JsonProcessingException {

        Long time = System.currentTimeMillis();
        String smsCode = makeCode();

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
    private String makeCode() {

        StringBuffer key = new StringBuffer();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            key.append(rand.nextInt(10));
        }

        return key.toString();
    }

    // 인증번호 검증
    public void verifySms(InfoRequest request, String userEmail, HttpSession session) {

        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.PHONE_DISCORD, ApplyErrorCode.PHONE_DISCORD.getMessage()));

        if (!isVerify(request)) {
            throw new ApplyException(ApplyErrorCode.AUTHENTICATION_FAILED, ApplyErrorCode.AUTHENTICATION_FAILED.getMessage());
        }

        redisSmsUtil.deleteSmsAuth(request.getPhone());
        session.setAttribute(SMS_USER_ID, user.getId());
    }

    // 인증번호 일치 여부
    private boolean isVerify(InfoRequest request) {

        return (redisSmsUtil.hasKey(request.getPhone()) &&
                redisSmsUtil.getSmsAuth(request.getPhone()).equals(request.getCode()));
    }
}