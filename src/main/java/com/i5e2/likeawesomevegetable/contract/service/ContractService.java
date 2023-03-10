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

        // RestTemplate ?????? ??????
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader ??????
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("eformsign_signature", contractSignature.getSignature());
        httpHeaders.setBearerAuth(contractSignature.getEncodedKey());

        // request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("execution_time", contractSignature.getExecutionTime());
        body.add("member_id", "bujjaf@gmail.com");
        // ????????? ???????????????

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

    // ?????? ???????????? ?????? newBuyingForm
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

        // ?????? ??????
        Integer pricePerQuantity = companyBuying.getBuyingPrice() / companyBuying.getBuyingQuantity();
        Long finalPrice = apply.getApplyQuantity() * pricePerQuantity;
        log.info("?????????:{}", finalPrice);

        // ?????? ??????
        String buyingShipping = companyBuying.getBuyingShipping();
        String shipping = buyingShipping.equals("BOXING") ? "?????? ??????" : buyingShipping.equals("TONBAG") ? "?????? ??????" : "?????? ??????";

        log.info("??????:{}", shipping);

        // ?????? ?????? -> ????????? ??????
        String vegName = itemJpaRepository.findByItemCode(companyBuying.getBuyingItem()).getItemName();
        log.info("vegName:{}", vegName);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", vegName); // ?????? ?????????
        data.put("applyQuantity", apply.getApplyQuantity()); // ?????? ?????? ??????
        data.put("pricePerQuantity", pricePerQuantity); // ??? ??? ??????
        data.put("finalPrice", finalPrice); // ?????? ??????
        data.put("shipping", shipping); // ?????? ??????
        data.put("receiverAddress", companyBuying.getReceiverAddress()); // ????????? ??????
        data.put("receiverName", companyBuying.getReceiverName()); // ????????? ??????
        data.put("receiverPhoneNo", companyBuying.getReceiverPhoneNo()); // ????????? ?????????
        data.put("companyId", companyBuying.getCompanyUser().getId());
        data.put("companyName", companyBuying.getCompanyUser().getCompanyName()); // ????????? ?????? ?????? ??????
        data.put("companyEmail", companyInfo.getEmail());
        data.put("companyPhone", companyInfo.getManaverPhoneNo());
        data.put("farmerId", apply.getUser().getFarmUser().getId());
        data.put("farmerName", apply.getUser().getFarmUser().getFarmName()); // ?????? ?????? ??????
        data.put("farmerEmail", apply.getUser().getEmail());
        data.put("farmerPhone", apply.getUser().getManaverPhoneNo());
        data.put("buyingPostId", companyBuying.getId());

        log.info("????????? ??????");

        return data;
    }

    // ?????? ????????? ?????? newAuctionForm
    public HashMap<String, Object> getAuctionNewContract(Long auctionId, Long biddingId) {
        FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                ); // ????????? ?????? ??????

        User farmInfo = userJpaRepository.findByFarmUserId(farmAuction.getFarmUser().getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.USER_NOT_FOUND,
                        AppErrorCode.USER_NOT_FOUND.getMessage())
                ); // ?????? ?????? ??????

        Standby standby = standByJpaRepository.findById(biddingId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.BIDDING_NOT_FOUND,
                        AppErrorCode.BIDDING_NOT_FOUND.getMessage())); //?????? ?????? ??????

        User companyInfo = userJpaRepository.findById(standby.getUser().getId())
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.USER_NOT_FOUND,
                        AppErrorCode.USER_NOT_FOUND.getMessage())
                ); // ?????? ?????? ??????

        // ?????? ??????
        String auctionShipping = farmAuction.getAuctionShipping();
        String shipping = auctionShipping.equals("BOXING") ? "?????? ??????" : auctionShipping.equals("TONBAG") ? "?????? ??????" : "?????? ??????";

        // ?????? ?????? -> ????????? ??????
        String vegName = null;
        try {
            vegName = itemJpaRepository.findByItemCode(farmAuction.getAuctionItem()).getItemName();
        } catch (Exception e) {
            throw new AwesomeVegeAppException(
                    AppErrorCode.ITEM_NOT_FOUND,
                    AppErrorCode.ITEM_NOT_FOUND.getMessage());
        }

        log.info("??????:{}", shipping);

        HashMap<String, Object> data = new HashMap<>();

        data.put("vegName", vegName); // ?????? ?????????
        data.put("auctionQuantity", farmAuction.getAuctionQuantity()); // ?????? ?????? ??????
        data.put("auctionHighestPrice", farmAuction.getAuctionHighestPrice()); // ?????? ?????????
        data.put("finalPrice", farmAuction.getAuctionHighestPrice()); // ?????? ??????
        data.put("shipping", shipping); // ?????? ??????

        data.put("farmerName", farmInfo.getFarmUser().getFarmName()); // ????????? ?????? ?????? ??????
        data.put("farmerEmail", farmInfo.getEmail());
        data.put("farmerPhone", farmInfo.getManaverPhoneNo());
        data.put("farmerId", farmInfo.getFarmUser().getId()); // farm_user ??????????????? ID

        data.put("companyName", standby.getUser().getCompanyUser().getCompanyName()); // ????????? ?????? ??????
        data.put("companyEmail", companyInfo.getEmail());
        data.put("companyPhone", companyInfo.getManaverPhoneNo());
        data.put("companyId", standby.getUser().getCompanyUser().getId()); // company_user ??????????????? id

        data.put("auctionPostId", farmAuction.getId());

        log.info("????????? ??????");

        return data;
    }

    // ????????? Document id ??????
    @Transactional
    public void saveContractDB(Map request) {
        log.info("????????? ???:{}", request.get("documentId"));

        String documentId = (String) request.get("documentId");
        String buyerId = (String) request.get("buyerId");
        String sellerId = (String) request.get("sellerId");
        String contractItem = (String) request.get("contractItem");
        Long contractQuantity = Long.valueOf((String) request.get("contractQuantity"));
        Long finalPrice = Long.valueOf((String) request.get("finalPrice"));
        Long buyingId = null;
        Long auctionId = null;

        if (request.get("contractType").equals("buying")) {
            // buying ????????????

            buyingId = Long.valueOf((String) request.get("buyingPostId"));

        } else if (request.get("contractType").equals("auction")) {
            // auction ????????????

            auctionId = Long.valueOf((String) request.get("auctionPostId"));
        }

        ContractInfo contractInfo = new ContractInfo(documentId, buyerId, sellerId, contractItem, contractQuantity, finalPrice, buyingId, auctionId);

        contractInfoJpaRepository.save(contractInfo);

    }


    // json
    public HashMap<String, Object> getJsonData(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

        // RestTemplate ?????? ??????
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader ??????
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getAccessToken());

        HttpEntity<?> requestMessage = new HttpEntity<>(httpHeaders);

        String url = "https://kr-api.eformsign.com/v2.0/api/documents/" + documentId + "?include_fields=true";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestMessage, String.class);

        JSONParser parser = new JSONParser();
        JSONObject info1 = (JSONObject) parser.parse(response.getBody());
        JSONArray fields = (JSONArray) info1.get("fields");
        JSONObject status = (JSONObject) info1.get("current_status");
        String step = (String) status.get("step_index"); //step 2??? ????????? ?????? X, 3?????? ??? ??? ??????, 4??? ?????? ??????
        String stepStatus = (String) status.get("step_name"); //name ???????????? ?????? ?????? ???????????? ???, ????????? ?????? ?????? ???????????? ???, ????????? ?????? ??????

        log.info("step:{}", step);
        log.info("stepSta:{}", stepStatus);
        log.info("data:{}", info1);

        HashMap<String, Object> data = new HashMap<>();
        data.put("step", step);
        data.put("stepStatus", status.get("step_name")); //name ???????????? ?????? ?????? ???????????? ???, ????????? ?????? ?????? ???????????? ???, ????????? ?????? ??????
        data.put("fields", fields);

        return data;
    }


    // ?????? ????????? ??????????????? ?????????
    // ?????? ?????? ????????? + contractType buying, auction?????? ???????????? ?????? ????????? ????????????
    @Transactional
    public String getContractStatus(String documentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

        // ???????????? ??????????????? ??????
        ContractInfo contractInfo = contractInfoJpaRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.CONTRACT_NOT_FOUND,
                        AppErrorCode.CONTRACT_NOT_FOUND.getMessage()));

        HashMap<String, Object> data = this.getJsonData(documentId);

        String step = (String) data.get("step");
        String stepStatus = (String) data.get("stepStatus");
        log.info("stepSta:{}", stepStatus);

        if (step.equals("2")) {
            if (stepStatus.equals("??????")) {

                contractInfo.updateStatus(ContractStatus.COMPANY_SIGN_WAITING);
                contractInfo.updateType(ContractType.BUYING);

                contractInfoJpaRepository.save(contractInfo);

                return "?????? ?????? ?????????"; // ??????: ?????? ????????? (step = 2, status = ??????)

            } else if (stepStatus.equals("??????")) {

                contractInfo.updateStatus(ContractStatus.FARMER_SIGN_WAITING);
                contractInfo.updateType(ContractType.AUCTION);

                contractInfoJpaRepository.save(contractInfo);

                return "?????? ?????? ?????????"; // ??????: ?????? ????????? (step = 2, status = ??????)
            }
        }

        if (step.equals("3")) {
            if (stepStatus.equals("??????")) {

                contractInfo.updateStatus(ContractStatus.FARMER_SIGN_WAITING);
                contractInfo.updateType(ContractType.BUYING);

                contractInfoJpaRepository.save(contractInfo);

                return "?????? ?????? ??????. ?????? ?????? ?????????"; // ??????: ?????? ????????? (step = 3, status = ??????)

            } else if (stepStatus.equals("??????")) {

                contractInfo.updateStatus(ContractStatus.COMPANY_SIGN_WAITING);
                contractInfo.updateType(ContractType.AUCTION);

                contractInfoJpaRepository.save(contractInfo);

                return "?????? ?????? ??????. ?????? ?????? ?????????"; // ??????: ?????? ????????? (step = 3, status = ??????)
            }
        }

        if (step.equals("4")) {

            if (contractInfo.getContractType().equals(ContractType.BUYING)) {
                // ?????? + ??????
                this.saveBuyingContract(documentId);
                this.buyingContractCompleted(contractInfo.getBuyingId());

            } else if (contractInfo.getContractType().equals(ContractType.AUCTION)) {
                // ?????? + ??????
                this.saveAuctionContract(documentId);
                this.auctionContractCompleted(contractInfo.getAuctionId());
            }

            return "?????? ???????????? ????????? ??????????????????. ????????? ?????????????????????.";
        }

        return "";
    }


    // ?????? ?????? ???????????? ????????? ??????
    public void buyingContractCompleted(Long buyingId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(buyingId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );

        companyBuying.updateStatusToEnd();

        companyBuyingJpaRepository.save(companyBuying);

        buyingService.applyEnd(companyBuying.getId());

        // + ????????? ?????? ??????????????
    }

    // ?????? ?????? ???????????? ?????????& ?????? ??????
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

        //+ ????????? ?????? ???????????????

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

        log.info("?????? ?????? ??????:{}", fields);

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