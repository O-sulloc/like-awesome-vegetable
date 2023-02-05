package com.i5e2.likeawesomevegetable.domain.contract;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Transactional(readOnly = true)
public class ContractService {

    private final ContractSignature contractSignature;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;

    private final StandByJpaRepository standByJpaRepository;
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

    // 계약서 생성 newForm
    public HashMap<String, Object> getBuyingNewContract(Long buyingId, Long applyId) {
        Optional<CompanyBuying> companyBuying = companyBuyingJpaRepository.findById(buyingId);
        Optional<User> companyInfo = userJpaRepository.findByCompanyUserId(companyBuying.get().getCompanyUser().getId());
        Optional<Apply> apply = applyJpaRepository.findById(applyId);

        // 가격 계산
        Integer pricePerQuantity = companyBuying.get().getBuyingPrice() / companyBuying.get().getBuyingQuantity();
        Long finalPrice = apply.get().getApplyQuantity() * pricePerQuantity;
        log.info("최종가:{}", finalPrice);

        // 운임 방식
        String buyingShipping = companyBuying.get().getBuyingShipping();
        String shipping = buyingShipping.equals("BOXING") ? "박스 포장" : buyingShipping.equals("TONBAG") ? "톤백 포장" : "콘티 포장";

        log.info("운임:{}", shipping);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", companyBuying.get().getBuyingItem()); // 구매 품목명
        data.put("applyQuantity", apply.get().getApplyQuantity()); // 농가 판매 수량
        data.put("pricePerQuantity", pricePerQuantity); // 톤 당 금액
        data.put("finalPrice", finalPrice); // 최종 금액
        data.put("shipping", shipping); // 운송 수단
        data.put("receiverAddress", companyBuying.get().getReceiverAddress()); // 수취인 주소
        data.put("receiverName", companyBuying.get().getReceiverName()); // 수취인 이름
        data.put("receiverPhoneNo", companyBuying.get().getReceiverPhoneNo()); // 수취인 폰번호
        data.put("companyName", companyBuying.get().getCompanyUser().getCompanyName()); // 모집글 올린 기업 정보
        data.put("companyEmail", companyInfo.get().getEmail());
        data.put("companyPhone", companyInfo.get().getManaverPhoneNo());
        data.put("farmerName", apply.get().getUser().getFarmUser().getFarmName()); // 참여 농가 정보
        data.put("farmerEmail", apply.get().getUser().getEmail());
        data.put("farmerPhone", apply.get().getUser().getManaverPhoneNo());

        return data;
    }

    // 계약서 생성 auction
    public HashMap<String, Object> getAuctionNewContract(Long auctionId, Long biddingId) {
        Optional<FarmAuction> farmAuction = farmAuctionJpaRepository.findById(auctionId);
        Optional<User> farmInfo = userJpaRepository.findByFarmUserId(farmAuction.get().getFarmUser().getId()); //농가 정보
        Optional<Standby> standby = standByJpaRepository.findById(biddingId);
        Optional<User> companyInfo = userJpaRepository.findById(standby.get().getUser().getId()); //입찰 기업 정보

        // 운임 방식
        String auctionShipping = farmAuction.get().getAuctionShipping();
        String shipping = auctionShipping.equals("BOXING") ? "박스 포장" : auctionShipping.equals("TONBAG") ? "톤백 포장" : "콘티 포장";

        log.info("운임:{}", shipping);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", farmAuction.get().getAuctionItem()); // 구매 품목명
        data.put("auctionQuantity", farmAuction.get().getAuctionQuantity()); // 농가 판매 수량
        data.put("auctionHighestPrice", farmAuction.get().getAuctionHighestPrice()); // 낙찰 최고가
        data.put("finalPrice", farmAuction.get().getAuctionHighestPrice()); // 최종 금액
        data.put("shipping", shipping); // 운송 수단

        data.put("farmerName", farmInfo.get().getFarmUser().getFarmName()); // 경매글 올린 농가 정보
        data.put("farmerEmail", farmInfo.get().getEmail());
        data.put("farmerPhone", farmInfo.get().getManaverPhoneNo());

        data.put("companyName", standby.get().getUser().getCompanyUser().getCompanyName()); // 입찰한 기업 정보
        data.put("companyEmail", companyInfo.get().getEmail());
        data.put("companyPhone", companyInfo.get().getManaverPhoneNo());

        return data;
    }

    // 계약서 Document id 저장
    @Transactional
    public void saveContractDB() {

    }

    // 계약 완료 후 계좌 정보 가져오기
    public HashMap<String, String> getContractInfo(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 셋팅
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getAccessToken());

        HttpEntity<?> requestMessage = new HttpEntity<>(httpHeaders);

        String url = "https://kr-api.eformsign.com/v2.0/api/documents/" + documentId + "?include_fields=true";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestMessage, String.class);

        JSONParser parser = new JSONParser();
        JSONObject info1 = (JSONObject) parser.parse(response.getBody());
        JSONArray info2 = (JSONArray) info1.get("fields");
        JSONObject account1 = (JSONObject) info2.get(6);
        JSONObject account2 = (JSONObject) info2.get(7);
        JSONObject account3 = (JSONObject) info2.get(8);

        HashMap<String, String> accountInfo = new HashMap<>();
        accountInfo.put("accountNo", (String) account1.get("value"));
        accountInfo.put("accountName", (String) account2.get("value"));
        accountInfo.put("accountOwnerName", (String) account3.get("value"));

        return accountInfo;
    }
}