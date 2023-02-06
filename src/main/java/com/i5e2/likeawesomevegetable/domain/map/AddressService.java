package com.i5e2.likeawesomevegetable.domain.map;

import com.i5e2.likeawesomevegetable.domain.map.exception.AddressErrorCode;
import com.i5e2.likeawesomevegetable.domain.map.exception.AddressException;
import com.i5e2.likeawesomevegetable.repository.CompanyUserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    @Value("${kakao.host}")
    private String host;

    @Value("${kakao.apiKey}")
    private String apiKey;

    private final CompanyUserJpaRepository companyUserJpaRepository;
    private final FarmUserRepository farmUserRepository;

    // 기업 사용자 주소 좌표로 변환
    public Positions getCompanyAddress() throws IOException, ParseException {
        List<CompanyAddress> addresses = companyUserJpaRepository.findAllBy();
        List<AddressInfo> addressInfos = new ArrayList<>();

        if (addresses == null || addresses.isEmpty()) {
            throw new AddressException(AddressErrorCode.COMPANY_ADDRESS_NOT_FOUND, AddressErrorCode.COMPANY_ADDRESS_NOT_FOUND.getMessage());
        }

        for (CompanyAddress address : addresses) {
            String loadAddress = URLEncoder.encode(address.getCompanyAddress(), "UTF-8");

            URL url = new URL(host + loadAddress);
            URLConnection conn = url.openConnection();

            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer jsonStr = new StringBuffer();

            String line;

            while ((line = rd.readLine()) != null) {
                jsonStr.append(line);
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject addressJson = (JSONObject) jsonParser.parse(String.valueOf(jsonStr));
            JSONArray documents = (JSONArray) addressJson.get("documents");

            for (Object document : documents) {
                addressJson = (JSONObject) document;

                AddressInfo addressInfo = AddressInfo.builder()
                        .id(address.getId())
                        .name(address.getCompanyName())
                        .x(addressJson.get("x").toString())
                        .y(addressJson.get("y").toString())
                        .build();

                addressInfos.add(addressInfo);
            }
        }

        return Positions.builder()
                .positions(addressInfos)
                .build();
    }

    // 농가 사용자 주소 좌표로 변환
    public Positions getFarmAddress() throws IOException, ParseException {

        List<FarmAddress> addresses = farmUserRepository.findAllBy();
        List<AddressInfo> addressInfos = new ArrayList<>();

        if (addresses == null || addresses.isEmpty()) {
            throw new AddressException(AddressErrorCode.FARM_ADDRESS_NOT_FOUND, AddressErrorCode.FARM_ADDRESS_NOT_FOUND.getMessage());
        }

        for (FarmAddress address : addresses) {
            String loadAddress = URLEncoder.encode(address.getFarmAddress(), "UTF-8");

            URL url = new URL(host + loadAddress);
            URLConnection conn = url.openConnection();

            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer jsonStr = new StringBuffer();

            String line;

            while ((line = rd.readLine()) != null) {
                jsonStr.append(line);
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject addressJson = (JSONObject) jsonParser.parse(String.valueOf(jsonStr));
            JSONArray documents = (JSONArray) addressJson.get("documents");

            for (Object document : documents) {
                addressJson = (JSONObject) document;

                AddressInfo addressInfo = AddressInfo.builder()
                        .id(address.getId())
                        .name(address.getFarmName())
                        .x(addressJson.get("x").toString())
                        .y(addressJson.get("y").toString())
                        .build();

                addressInfos.add(addressInfo);
            }
        }

        return Positions.builder()
                .positions(addressInfos)
                .build();
    }
}
