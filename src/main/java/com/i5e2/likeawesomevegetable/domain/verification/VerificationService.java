package com.i5e2.likeawesomevegetable.domain.verification;

import com.i5e2.likeawesomevegetable.domain.verification.dto.SendVerifyEmailResponse;
import com.i5e2.likeawesomevegetable.domain.verification.dto.VerifyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationService {
    private final JavaMailSender javaMailSender;
    private final RedisEmailUtil redisEmailUtil;
    private final String ePw = createKey();

    // 메일전송 ID
    @Value("${spring.mail.username}")
    private String id;

    // 사업자 등록정보 검증 ApiKey
    @Value("${business-api.key}")
    private String apiKey;

    /*     url 검증     */
    public VerifyResponse verifyUrl(String verificationUrl) {
        int code = 0;
        try {
            URL obj = new URL(verificationUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            code = con.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (code >= 200 && code < 300) {
            return VerifyResponse.of(Verification.VERIFIED, "검증 성공");
        }
        return VerifyResponse.of(Verification.NOT_VERIFIED, "검증 실패");
    }

    /*     사업자 등록번호 확인     */
    public VerifyResponse verifyCompany(String businessNo, String startDate, String managerName) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("b_no", businessNo);
        map.put("start_dt", startDate);
        map.put("p_nm", managerName);
        map.put("p_nm2", "");
        map.put("b_nm", "");
        map.put("corp_no", "");
        map.put("b_sector", "");
        map.put("b_type", "");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(map);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("businesses", jsonArray);

        RequestBody body = RequestBody.create(jsonObject.toJSONString(), mediaType);
        Request request = new Request.Builder()
                .url("http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=" + apiKey)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseToString = response.body().string();

        // 사업자 등록정보 진위 확인 리턴 JSON 중 진위 확인 결과 코드 추출
        // 01인 경우 valid
        int indexOfValid = responseToString.indexOf("\"valid\"");
        String validState = responseToString.substring(indexOfValid + 9, 90);

        // 사업자 등록정보 진위 확인 리턴 JSON 중 사업장 상태 코드 추출
        // 01인 경우 계속사업자
        int indexOfBSttCd = responseToString.indexOf("\"b_stt_cd\"");
        String BusinessState = responseToString.substring(indexOfBSttCd + 12, indexOfBSttCd + 14);

        if (validState.equals("01") && BusinessState.equals("01")) {
            return VerifyResponse.of(Verification.VERIFIED, "검증 성공");
        }
        return VerifyResponse.of(Verification.NOT_VERIFIED, "검증 성공");
    }


    /*     이메일 검증 코드 전송 로직     */
    public SendVerifyEmailResponse sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            // 코드 유효시간 3분
            redisEmailUtil.setDataExpire(ePw, to, 60 * 3L);
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return SendVerifyEmailResponse.of(to, "인증 번호가 전송되었습니다.");
    }

    /*     이메일 코드 확인 로직     */
    public VerifyResponse verifyEmail(String key) {
        String memberEmail = redisEmailUtil.getData(key);
        if (memberEmail == null) {
            return VerifyResponse.of(Verification.NOT_VERIFIED, "잘못된 인증번호입니다.");
        }
        redisEmailUtil.deleteData(key);
        return VerifyResponse.of(Verification.VERIFIED, "인증 성공");
    }

    /*     메일 내용 작성 로직     */
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : " + to);
        log.info("인증 번호 : " + ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("멋쟁이 채소처럼 정회원 등록 이메일 인증 코드: ");

        String msg = "";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress(id, "prac_Admin"));

        return message;
    }

    /*     인증코드 생성 로직     */
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            // 6자리 난수
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }


}
