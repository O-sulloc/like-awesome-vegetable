package com.i5e2.likeawesomevegetable.contract.service;

import com.i5e2.likeawesomevegetable.alarm.Alarm;
import com.i5e2.likeawesomevegetable.alarm.AlarmJpaRepository;
import com.i5e2.likeawesomevegetable.alarm.dto.AlarmDetail;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.company.apply.Apply;
import com.i5e2.likeawesomevegetable.company.apply.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.company.buying.CompanyBuying;
import com.i5e2.likeawesomevegetable.company.buying.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.company.buying.service.BuyingService;
import com.i5e2.likeawesomevegetable.contract.ContractInfo;
import com.i5e2.likeawesomevegetable.contract.api.ContractSignature;
import com.i5e2.likeawesomevegetable.contract.dto.ContractStatus;
import com.i5e2.likeawesomevegetable.contract.dto.ContractType;
import com.i5e2.likeawesomevegetable.contract.repository.ContractInfoJpaRepository;
import com.i5e2.likeawesomevegetable.farm.auction.FarmAuction;
import com.i5e2.likeawesomevegetable.farm.auction.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.farm.bidding.Standby;
import com.i5e2.likeawesomevegetable.farm.bidding.repository.StandByJpaRepository;
import com.i5e2.likeawesomevegetable.item.repository.ItemJpaRepository;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
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
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Transactional(readOnly = true)
public class ContractService {
    private final AlarmJpaRepository alarmJpaRepository;
    private final ContractSignature contractSignature;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;
    private final ContractInfoJpaRepository contractInfoJpaRepository;
    private final StandByJpaRepository standByJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final BuyingService buyingService;
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

    // 모집 계약서를 생성 newBuyingForm
    public HashMap<String, Object> getBuyingNewContract(Long buyingId, Long applyId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(buyingId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );

        User companyInfo = userJpaRepository.findByCompanyUserId(companyBuying.getCompanyUser().getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.USER_NOT_FOUND,
                        AppErrorCode.USER_NOT_FOUND.getMessage())
                );

        //Optional<User> companyInfo = userJpaRepository.findByCompanyUserId(companyBuying.get().getCompanyUser().getId());

        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.APPLY_NOT_FOUND,
                        AppErrorCode.APPLY_NOT_FOUND.getMessage()));

        //Optional<Apply> apply = applyJpaRepository.findById(applyId);

        // 가격 계산
        Integer pricePerQuantity = companyBuying.getBuyingPrice() / companyBuying.getBuyingQuantity();
        Long finalPrice = apply.getApplyQuantity() * pricePerQuantity;
        log.info("최종가:{}", finalPrice);

        // 운임 방식
        String buyingShipping = companyBuying.getBuyingShipping();
        String shipping = buyingShipping.equals("BOXING") ? "박스 포장" : buyingShipping.equals("TONBAG") ? "톤백 포장" : "콘티 포장";

        log.info("운임:{}", shipping);

        // 품목 코드 -> 글자로 변경
        String vegName = itemJpaRepository.findByItemCode(companyBuying.getBuyingItem()).getItemName();
        log.info("vegName:{}", vegName);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", vegName); // 구매 품목명
        data.put("applyQuantity", apply.getApplyQuantity()); // 농가 판매 수량
        data.put("pricePerQuantity", pricePerQuantity); // 톤 당 금액
        data.put("finalPrice", finalPrice); // 최종 금액
        data.put("shipping", shipping); // 운송 수단
        data.put("receiverAddress", companyBuying.getReceiverAddress()); // 수취인 주소
        data.put("receiverName", companyBuying.getReceiverName()); // 수취인 이름
        data.put("receiverPhoneNo", companyBuying.getReceiverPhoneNo()); // 수취인 폰번호
        data.put("companyId", companyBuying.getCompanyUser().getId());
        data.put("companyName", companyBuying.getCompanyUser().getCompanyName()); // 모집글 올린 기업 정보
        data.put("companyEmail", companyInfo.getEmail());
        data.put("companyPhone", companyInfo.getManaverPhoneNo());
        data.put("farmerId", apply.getUser().getFarmUser().getId());
        data.put("farmerName", apply.getUser().getFarmUser().getFarmName()); // 참여 농가 정보
        data.put("farmerEmail", apply.getUser().getEmail());
        data.put("farmerPhone", apply.getUser().getManaverPhoneNo());
        data.put("buyingPostId", companyBuying.getId());

        log.info("서비스 탈출");

        return data;
    }

    // 경매 계약서 생성 newAuctionForm
    public HashMap<String, Object> getAuctionNewContract(Long auctionId, Long biddingId) {
        FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                ); // 경매글 존재 여부

        User farmInfo = userJpaRepository.findByFarmUserId(farmAuction.getFarmUser().getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.USER_NOT_FOUND,
                        AppErrorCode.USER_NOT_FOUND.getMessage())
                ); // 농가 존재 여부

        Standby standby = standByJpaRepository.findById(biddingId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.BIDDING_NOT_FOUND,
                        AppErrorCode.BIDDING_NOT_FOUND.getMessage())); //입찰 존재 여부

        User companyInfo = userJpaRepository.findById(standby.getUser().getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.USER_NOT_FOUND,
                        AppErrorCode.USER_NOT_FOUND.getMessage())
                ); // 기업 존재 여부

        // 운임 방식
        String auctionShipping = farmAuction.getAuctionShipping();
        String shipping = auctionShipping.equals("BOXING") ? "박스 포장" : auctionShipping.equals("TONBAG") ? "톤백 포장" : "콘티 포장";

        // 품목 코드 -> 글자로 변경
        String vegName = null;
        try {
            vegName = itemJpaRepository.findByItemCode(farmAuction.getAuctionItem()).getItemName();
        } catch (Exception e) {
            throw new AwesomeVegeAppException(
                    AppErrorCode.ITEM_NOT_FOUND,
                    AppErrorCode.ITEM_NOT_FOUND.getMessage());
        }

        log.info("운임:{}", shipping);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", vegName); // 구매 품목명
        data.put("auctionQuantity", farmAuction.getAuctionQuantity()); // 농가 판매 수량
        data.put("auctionHighestPrice", farmAuction.getAuctionHighestPrice()); // 낙찰 최고가
        data.put("finalPrice", farmAuction.getAuctionHighestPrice()); // 최종 금액
        data.put("shipping", shipping); // 운송 수단

        data.put("farmerName", farmInfo.getFarmUser().getFarmName()); // 경매글 올린 농가 정보
        data.put("farmerEmail", farmInfo.getEmail());
        data.put("farmerPhone", farmInfo.getManaverPhoneNo());
        data.put("farmerId", farmInfo.getFarmUser().getId()); // farm_user 테이블에서 ID

        data.put("companyName", standby.getUser().getCompanyUser().getCompanyName()); // 입찰한 기업 정보
        data.put("companyEmail", companyInfo.getEmail());
        data.put("companyPhone", companyInfo.getManaverPhoneNo());
        data.put("companyId", standby.getUser().getCompanyUser().getId()); // company_user 테이블에서 id

        data.put("auctionPostId", farmAuction.getId());

        log.info("서비스 탈출");

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
        Long buyingId = null;
        Long auctionId = null;

        if (request.get("contractType").equals("buying")) {
            // buying 계약이면

            buyingId = Long.valueOf((String) request.get("buyingPostId"));

        } else if (request.get("contractType").equals("auction")) {
            // auction 계약이면

            auctionId = Long.valueOf((String) request.get("auctionPostId"));
        }

        ContractInfo contractInfo = new ContractInfo(documentId, buyerId, sellerId, contractItem, contractQuantity, finalPrice, buyingId, auctionId);

        contractInfoJpaRepository.save(contractInfo);

    }


    // json
    public HashMap<String, Object> getJsonData(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

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

        log.info("step:{}", step);
        log.info("stepSta:{}", stepStatus);
        log.info("data:{}", info1);

        HashMap<String, Object> data = new HashMap<>();
        data.put("step", step);
        data.put("stepStatus", status.get("step_name")); //name 기업이면 기업 서명 기다리는 중, 농가면 농가 서명 기다리는 중, 완료면 계약 완료
        data.put("fields", fields);

        return data;
    }


    // 계약 상태만 리턴해주는 메서드
    // 계약 상태 완료면 + contractType buying, auction으로 구분해서 밑에 메서드 불러오게
    @Transactional
    public String getContractStatus(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

        // 존재하는 계약서인지 확인
        ContractInfo contractInfo = contractInfoJpaRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.CONTRACT_NOT_FOUND,
                        AppErrorCode.CONTRACT_NOT_FOUND.getMessage()));

        HashMap<String, Object> data = this.getJsonData(documentId);

        String step = (String) data.get("step");
        String stepStatus = (String) data.get("stepStatus");
        log.info("stepSta:{}", stepStatus);

        if (step.equals("2")) {
            if (stepStatus.equals("기업")) {

                contractInfo.updateStatus(ContractStatus.COMPANY_SIGN_WAITING);
                contractInfo.updateType(ContractType.BUYING);

                contractInfoJpaRepository.save(contractInfo);

                return "기업 서명 대기중"; // 모집: 기업 서명중 (step = 2, status = 기업)

            } else if (stepStatus.equals("농가")) {

                contractInfo.updateStatus(ContractStatus.FARMER_SIGN_WAITING);
                contractInfo.updateType(ContractType.AUCTION);

                contractInfoJpaRepository.save(contractInfo);

                return "농가 서명 대기중"; // 경매: 농가 서명중 (step = 2, status = 농가)
            }
        }

        if (step.equals("3")) {
            if (stepStatus.equals("농가")) {

                contractInfo.updateStatus(ContractStatus.FARMER_SIGN_WAITING);
                contractInfo.updateType(ContractType.BUYING);

                contractInfoJpaRepository.save(contractInfo);

                return "기업 서명 완료. 농가 서명 대기중"; // 모집: 농가 서명중 (step = 3, status = 농가)

            } else if (stepStatus.equals("기업")) {

                contractInfo.updateStatus(ContractStatus.COMPANY_SIGN_WAITING);
                contractInfo.updateType(ContractType.AUCTION);

                contractInfoJpaRepository.save(contractInfo);

                return "농가 서명 완료. 기업 서명 대기중"; // 경매: 기업 서명중 (step = 3, status = 기업)
            }
        }

        if (step.equals("4")) {

            if (contractInfo.getContractType().equals(ContractType.BUYING)) {
                // 완료 + 모집
                this.saveBuyingContract(documentId);
                this.buyingContractCompleted(contractInfo.getBuyingId());

            } else if (contractInfo.getContractType().equals(ContractType.AUCTION)) {
                // 완료 + 경매
                this.saveAuctionContract(documentId);
                this.auctionContractCompleted(contractInfo.getAuctionId());
            }

            return "모든 참여자가 서명을 완료했습니다. 계약이 체결되었습니다.";
        }

        return "";
    }


    // 모집 계약 완료되면 모집글 마감
    public void buyingContractCompleted(Long buyingId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(buyingId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );

        companyBuying.updateStatusToEnd();

        companyBuyingJpaRepository.save(companyBuying);

        buyingService.applyEnd(companyBuying.getId());

        // + 계약서 상태 업데이트??
    }

    // 경매 계약 완료되면 경매글& 입찰 마감
    public void auctionContractCompleted(Long auctionId) {

        FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );

        farmAuction.updateStatusToEnd();

        farmAuctionJpaRepository.save(farmAuction);

        // TODO - alarm

        List<User> list = standByJpaRepository.selectByFarmAuctionId(farmAuction.getId());
        for (int i = 0; i < list.size(); i++) {
            Alarm alarm = Alarm.builder()
                    .alarmDetail(AlarmDetail.AUCTION)
                    .alarmTriggerId(farmAuction.getId())
                    .alarmRead(Boolean.FALSE)
                    .alarmSenderId(farmAuction.getFarmUser().getId())
                    .user(list.get(i))
                    .build();
            alarmJpaRepository.save(alarm);
        }

        standByJpaRepository.findAllByFarmAuctionId(farmAuction.getId()).forEach(Standby::updateStatusToEnd);

        //+ 계약서 상태 업데이트???

    }

    public void saveAuctionContract(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        HashMap<String, Object> data = this.getJsonData(documentId);
        JSONArray fields = (JSONArray) data.get("fields");

        JSONObject buyerAddress1 = (JSONObject) fields.get(5);
        JSONObject accountNo1 = (JSONObject) fields.get(8);
        JSONObject accountName1 = (JSONObject) fields.get(9);
        JSONObject accountOwnerName1 = (JSONObject) fields.get(10);
        JSONObject contractDate1 = (JSONObject) fields.get(21);

        String buyerAddress = (String) buyerAddress1.get("value");
        String accountNo = (String) accountNo1.get("value");
        String bankName = (String) accountName1.get("value");
        String accountOwnerName = (String) accountOwnerName1.get("value");
        String contractDate = (String) contractDate1.get("value");

        ContractInfo contractInfo = contractInfoJpaRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.CONTRACT_NOT_FOUND,
                        AppErrorCode.CONTRACT_NOT_FOUND.getMessage()));

        contractInfo.updateContractInfo(buyerAddress, accountNo, bankName, accountOwnerName, contractDate);

        contractInfoJpaRepository.save(contractInfo);

    }

    public void saveBuyingContract(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {
        HashMap<String, Object> data = this.getJsonData(documentId);
        JSONArray fields = (JSONArray) data.get("fields");

        log.info("이제 진짜 파싱:{}", fields);

        JSONObject buyerAddress1 = (JSONObject) fields.get(3);
        JSONObject accountNo1 = (JSONObject) fields.get(6);
        JSONObject accountName1 = (JSONObject) fields.get(7);
        JSONObject accountOwnerName1 = (JSONObject) fields.get(8);
        JSONObject contractDate1 = (JSONObject) fields.get(21);

        String buyerAddress = (String) buyerAddress1.get("value");
        String accountNo = (String) accountNo1.get("value");
        String bankName = (String) accountName1.get("value");
        String accountOwnerName = (String) accountOwnerName1.get("value");
        String contractDate = (String) contractDate1.get("value");

        ContractInfo contractInfo = contractInfoJpaRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.CONTRACT_NOT_FOUND,
                        AppErrorCode.CONTRACT_NOT_FOUND.getMessage()));

        contractInfo.updateContractInfo(buyerAddress, accountNo, bankName, accountOwnerName, contractDate);

        contractInfoJpaRepository.save(contractInfo);
    }

}