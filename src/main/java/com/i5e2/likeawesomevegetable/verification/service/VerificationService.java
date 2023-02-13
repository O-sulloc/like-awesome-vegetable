package com.i5e2.likeawesomevegetable.verification.service;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.payment.point.service.UserPointService;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.dto.UserType;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.user.company.CompanyUser;
import com.i5e2.likeawesomevegetable.user.company.repository.CompanyUserJpaRepository;
import com.i5e2.likeawesomevegetable.user.farm.FarmUser;
import com.i5e2.likeawesomevegetable.user.farm.repository.FarmUserRepository;
import com.i5e2.likeawesomevegetable.verification.RedisEmailUtil;
import com.i5e2.likeawesomevegetable.verification.UserVerification;
import com.i5e2.likeawesomevegetable.verification.dto.*;
import com.i5e2.likeawesomevegetable.verification.repository.UserVerificationJpaRepository;
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
    private final UserJpaRepository userJpaRepository;
    private final UserVerificationJpaRepository userVerificationJpaRepository;
    private final CompanyUserJpaRepository companyUserJpaRepository;
    private final FarmUserRepository farmUserRepository;
    private final JavaMailSender javaMailSender;
    private final RedisEmailUtil redisEmailUtil;
    private final UserPointService userPointService;

    // 메일전송 ID
    @Value("${spring.mail.username}")
    private String id;

    // 사업자 등록정보 검증 ApiKey
    @Value("${business-api.key}")
    private String apiKey;

    /*     농가 정회원 등록     */
    public Result<VerifyUserResponse> verifyFarmUser(VerifyFarmUserRequest verifyFarmUserRequest, String loginEmail) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 로그인 유저 검증 테이블 확인
        UserVerification loginUserVerification = validateUserVerification(loginUser);

        if (Verification.VERIFIED.equals(loginUserVerification.getVerificationEmail())) {
            // 가입된 유저 농가 정회원 등록
            FarmUser farmUser = FarmUser.makeFarmUser(verifyFarmUserRequest);
            farmUserRepository.save(farmUser);

            // User에 농가 정회원 정보, UserType 변경
            loginUser.updateUserType(UserType.ROLE_FARM);
            loginUser.updateFarmUser(farmUser);
            userJpaRepository.save(loginUser);

            // loginUserUserVerification 삭제
            userVerificationJpaRepository.delete(loginUserVerification);

            // 포인트 테이블 생성
            userPointService.addUserPointInfo(loginUser);

            return Result.success(VerifyUserResponse.of(loginEmail, farmUser.getFarmOwnerName(), "농가 정회원 등록 성공"));
        }
        return Result.error(VerifyUserResponse.of(loginEmail, verifyFarmUserRequest.getFarmOwnerName(), "Email 검증을 완료하세요."));
    }

    /*     기업 정회원 등록     */
    public Result<VerifyUserResponse> verifyCompanyUser(VerifyCompanyUserRequest verifyCompanyUserRequest, String loginEmail) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 로그인 유저 검증 테이블 확인
        UserVerification loginUserVerification = validateUserVerification(loginUser);

        // Email 검증 확인
        if (Verification.NOT_VERIFIED.equals(loginUserVerification.getVerificationEmail())) {
            return Result.error(VerifyUserResponse.of(loginEmail, verifyCompanyUserRequest.getCompanyName(), "이메일 검증을 완료하세요."));
        }

        // 기업 홈페이지 Url 검증 확인
        if (Verification.NOT_VERIFIED.equals(loginUserVerification.getVerificationUrl())) {
            return Result.error(VerifyUserResponse.of(loginEmail, verifyCompanyUserRequest.getCompanyName(), "url 검증을 완료하세요."));
        }

        // 사업자 등록정보 검증 확인
        if (Verification.NOT_VERIFIED.equals(loginUserVerification.getVerificationBusiness())) {
            return Result.error(VerifyUserResponse.of(loginEmail, verifyCompanyUserRequest.getCompanyName(), "사업자 등록정보 검증을 완료하세요."));
        }

        // 가입된 유저 기업 정회원 등록
        CompanyUser companyUser = CompanyUser.makeCompanyUser(verifyCompanyUserRequest);
        companyUserJpaRepository.save(companyUser);

        // User에 기업 정회원 정보, UserType 변경
        loginUser.updateUserType(UserType.ROLE_COMPANY);
        loginUser.updateCompanyUser(companyUser);
        userJpaRepository.save(loginUser);

        // loginUserUserVerification 삭제
        userVerificationJpaRepository.delete(loginUserVerification);

        // 포인트 테이블 생성
        userPointService.addUserPointInfo(loginUser);

        return Result.success(VerifyUserResponse.of(loginEmail, companyUser.getCompanyName(), "기업 정회원 등록 성공"));
    }

    /*     url 검증     */
    public Result<VerifyResponse> verifyUrl(String verificationUrl, String loginEmail) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 로그인 유저 검증 테이블 확인
        UserVerification loginUserVerification = validateUserVerification(loginUser);

        // 연결 시간 설정 2초
        int TIMEOUT_VALUE = 2000;

        int code = 0;
        try {
            URL obj = new URL(verificationUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(TIMEOUT_VALUE);
            con.setReadTimeout(TIMEOUT_VALUE);
            code = con.getResponseCode();
        } catch (Exception e) {
        }
        if (code >= 200 && code < 300) {
            loginUserVerification.makeUrlVerification(Verification.VERIFIED);
            userVerificationJpaRepository.save(loginUserVerification);
            return Result.success(VerifyResponse.of(loginUserVerification.getVerificationUrl(), "인증 성공"));
        }
        loginUserVerification.makeUrlVerification(Verification.NOT_VERIFIED);
        userVerificationJpaRepository.save(loginUserVerification);
        return Result.error(VerifyResponse.of(loginUserVerification.getVerificationUrl(), "유효하지 않은 url 입니다."));
    }

    /*     사업자 등록번호 확인     */
    public Result<VerifyResponse> verifyCompany(String businessNo, String startDate, String managerName, String loginEmail) throws IOException {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 로그인 유저 검증 테이블 확인
        UserVerification loginUserVerification = validateUserVerification(loginUser);

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
            loginUserVerification.makeBusinessVerification(Verification.VERIFIED);
            userVerificationJpaRepository.save(loginUserVerification);
            return Result.success(VerifyResponse.of(loginUserVerification.getVerificationBusiness(), "인증 성공"));
        }
        loginUserVerification.makeBusinessVerification(Verification.NOT_VERIFIED);
        userVerificationJpaRepository.save(loginUserVerification);
        return Result.success(VerifyResponse.of(loginUserVerification.getVerificationBusiness(), "유효하지 않은 사업자 등록정보입니다."));
    }

    /*     이메일 검증 코드 전송 로직     */
    public Result<SendVerifyEmailResponse> sendSimpleMessage(String to) throws Exception {
        String ePw = createKey();
        MimeMessage message = createMessage(to, ePw);
        try {
            // 코드 유효시간 3분
            redisEmailUtil.setDataExpire(to, ePw, 60 * 3L);
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return Result.success(SendVerifyEmailResponse.of(to, "인증 번호가 전송되었습니다."));
    }

    /*     이메일 코드 확인 로직     */
    public Result<VerifyResponse> verifyEmail(String keyEmail, String key, String loginEmail) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 로그인 유저 검증 테이블 확인
        UserVerification loginUserVerification = validateUserVerification(loginUser);

        boolean verifyEmailCode = redisEmailUtil.hasKey(keyEmail) && redisEmailUtil.getEmailCode(keyEmail).equals(key);

        if (!verifyEmailCode) {
            redisEmailUtil.deleteData(keyEmail);
            loginUserVerification.makeEmailVerification(Verification.NOT_VERIFIED);
            userVerificationJpaRepository.save(loginUserVerification);
            return Result.error(VerifyResponse.of(Verification.NOT_VERIFIED, "잘못된 인증번호입니다."));
        }
        redisEmailUtil.deleteData(keyEmail);
        loginUserVerification.makeEmailVerification(Verification.VERIFIED);
        userVerificationJpaRepository.save(loginUserVerification);
        return Result.success(VerifyResponse.of(loginUserVerification.getVerificationEmail(), "인증 성공"));
    }

    /*     메일 내용 작성 로직     */
    public MimeMessage createMessage(String to, String ePw) throws MessagingException, UnsupportedEncodingException {
        redisEmailUtil.deleteData(ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("멋쟁이 채소처럼 정회원 등록 이메일 인증 코드");

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

    // 로그인 이메일 확인
    private User validateLoginUser(String loginEmail) {
        User loginUser = userJpaRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.LOGIN_USER_NOT_FOUND,
                        AppErrorCode.LOGIN_USER_NOT_FOUND.getMessage()
                ));
        return loginUser;
    }

    private UserVerification validateUserVerification(User user) {
        UserVerification loginUserVerification = userVerificationJpaRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.VERIFICATION_DISABLE,
                        AppErrorCode.VERIFICATION_DISABLE.getMessage())
                );
        return loginUserVerification;
    }

}
