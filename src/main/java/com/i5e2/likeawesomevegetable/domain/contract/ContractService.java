package com.i5e2.likeawesomevegetable.domain.contract;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyErrorCode;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
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
import java.util.Map;
import java.util.NoSuchElementException;
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
    private final ContractInfoJpaRepository contractInfoJpaRepository;
    private final StandByJpaRepository standByJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
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
        data.put("companyId", companyBuying.get().getCompanyUser().getId());
        data.put("companyName", companyBuying.get().getCompanyUser().getCompanyName()); // 모집글 올린 기업 정보
        data.put("companyEmail", companyInfo.get().getEmail());
        data.put("companyPhone", companyInfo.get().getManaverPhoneNo());
        data.put("farmerId", apply.get().getUser().getFarmUser().getId());
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

        // 품목 코드 -> 글자로 변경
        String vegName = itemJpaRepository.findByItemCode(farmAuction.get().getAuctionItem()).getItemName();

        log.info("운임:{}", shipping);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", vegName); // 구매 품목명
        data.put("auctionQuantity", farmAuction.get().getAuctionQuantity()); // 농가 판매 수량
        data.put("auctionHighestPrice", farmAuction.get().getAuctionHighestPrice()); // 낙찰 최고가
        data.put("finalPrice", farmAuction.get().getAuctionHighestPrice()); // 최종 금액
        data.put("shipping", shipping); // 운송 수단

        data.put("farmerName", farmInfo.get().getFarmUser().getFarmName()); // 경매글 올린 농가 정보
        data.put("farmerEmail", farmInfo.get().getEmail());
        data.put("farmerPhone", farmInfo.get().getManaverPhoneNo());
        data.put("farmerId", farmInfo.get().getFarmUser().getId()); // farm_user 테이블에서 ID

        data.put("companyName", standby.get().getUser().getCompanyUser().getCompanyName()); // 입찰한 기업 정보
        data.put("companyEmail", companyInfo.get().getEmail());
        data.put("companyPhone", companyInfo.get().getManaverPhoneNo());
        data.put("companyId", standby.get().getUser().getCompanyUser().getId()); // company_user 테이블에서 id

        return data;
    }

    // 계약서 Document id 저장
    @Transactional
    public void saveContractDB(Map request) {
        log.info("서비스 옴:{}", request.get("documentId"));

        String documentId = (String) request.get("documentId");
        String buyerId = (String) request.get("buyerId");
        String sellerId = (String) request.get("sellerId");
        String contractItem = (String) request.get("contractItem");
        Long contractQuantity = Long.valueOf((String) request.get("contractQuantity"));
        Long finalPrice = Long.valueOf((String) request.get("finalPrice"));

        ContractInfo contractInfo = new ContractInfo(documentId, buyerId, sellerId, contractItem, contractQuantity, finalPrice);

        contractInfoJpaRepository.save(contractInfo);

    }

    // 계약 단계별로 계약서 정보 저장
    @Transactional
    public void getContractInfo(String documentId, Long auctionId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

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
        JSONArray fields = (JSONArray) info1.get("fields");
        JSONObject status = (JSONObject) info1.get("current_status");
        String step = (String) status.get("step_index"); //step 2면 아무도 서명 X, 3이면 한 명 서명, 4면 계약 완료
        String stepStatus = (String) status.get("step_name"); //name 기업이면 기업 서명 기다리는 중, 농가면 농가 서명 기다리는 중, 완료면 계약 완료

        if (step.equals("2")) {
            if (stepStatus.equals("기업")) {
                log.info("기업 서명 대기중"); // 모집: 기업 서명중 (step = 2, status = 기업)

            } else if (stepStatus.equals("농가")) {
                log.info("농가 서명 대기중"); // 경매: 농가 서명중 (step = 2, status = 농가)
            }
        }

        if (step.equals("3")) {
            if (stepStatus.equals("농가")) {
                log.info("기업 서명 완료. 농가 서명 대기중"); // 모집: 농가 서명중 (step = 3, status = 농가)

            } else if (stepStatus.equals("농가")) {
                log.info("농가 서명 완료. 기업 서명 대기중"); // 경매: 기업 서명중 (step = 3, status = 기업)
            }
        }

        if (step.equals("4")) {
            // 계약 완료

            JSONObject buyerAddress1 = (JSONObject) fields.get(3);
            JSONObject buyerName1 = (JSONObject) fields.get(12);
            JSONObject buyerPhoneNo1 = (JSONObject) fields.get(11);
            JSONObject accountNo1 = (JSONObject) fields.get(6);
            JSONObject accountName1 = (JSONObject) fields.get(7);
            JSONObject accountOwnerName1 = (JSONObject) fields.get(8);
            JSONObject farmAddress1 = (JSONObject) fields.get(16);
            JSONObject sellerName1 = (JSONObject) fields.get(19);
            JSONObject sellerPhoneNo1 = (JSONObject) fields.get(19);
            JSONObject contractDate1 = (JSONObject) fields.get(21);

            String buyerAddress = (String) buyerAddress1.get("value");
            String buyerName = (String) buyerName1.get("value");
            String buyerPhoneNo = (String) buyerPhoneNo1.get("value");
            String accountNo = (String) accountNo1.get("value");
            String bankName = (String) accountName1.get("value");
            String accountOwnerName = (String) accountOwnerName1.get("value");
            String farmAddress = (String) farmAddress1.get("value");
            String sellerName = (String) sellerName1.get("value");
            String sellerPhoneNo = (String) sellerPhoneNo1.get("value");
            String contractDate = (String) contractDate1.get("value");

            ContractInfo contractInfo = contractInfoJpaRepository.findByDocumentId(documentId)
                    .orElseThrow(() -> new NoSuchElementException("없는 계약서"));

            contractInfo.updateContractInfo(buyerAddress, buyerName, buyerPhoneNo, accountNo, bankName, accountOwnerName, farmAddress,
                    sellerName, sellerPhoneNo, contractDate);

            // 동시성 처리 redis

            contractInfoJpaRepository.save(contractInfo);

            // 옥션 상태 업데이
            FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionId)
                    .orElseThrow(() -> new ApplyException(
                            ApplyErrorCode.POST_NOT_FOUND,
                            ApplyErrorCode.POST_NOT_FOUND.getMessage())
                    );

            farmAuction.updateStatusToEnd();

            farmAuctionJpaRepository.save(farmAuction);
        }

    }
}