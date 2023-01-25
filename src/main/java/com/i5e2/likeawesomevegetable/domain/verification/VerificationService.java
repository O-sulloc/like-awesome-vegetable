package com.i5e2.likeawesomevegetable.domain.verification;

import com.i5e2.likeawesomevegetable.domain.user.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationService {

    // url 검증
    public boolean verifyUrl(String url) {
        int code = 0;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            code = con.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (code >= 200 && code < 300) {
            return true;
        }
        return false;
    }


    // 사업자 등록번호 확인
    public UserType verifyCompany(String businessNo, String startDate, String managerName) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        String apiKey = "M8mptI5SJqVeNg7sy4u5GJiNk%2BYA0BuBHFVc5EJBnL0aw8dlM91QLeci%2FODXwc2hQq%2FdOwTAI432wv5QHMApxg%3D%3D";

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
        int indexOfValid = responseToString.indexOf("\"valid\"");
        int indexOfBSttCd = responseToString.indexOf("\"b_stt_cd\"");
        String validState = responseToString.substring(indexOfValid + 9, 90);
        String BusinessState = responseToString.substring(indexOfBSttCd + 12, indexOfBSttCd + 14);

        if (validState.equals("01") && BusinessState.equals("01")) {
            return UserType.VERIFIED_COMPANY;
        }
        return UserType.COMPANY;
    }

}
