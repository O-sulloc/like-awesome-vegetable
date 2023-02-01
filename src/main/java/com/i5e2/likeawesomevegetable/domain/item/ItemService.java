package com.i5e2.likeawesomevegetable.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${kamis.host}")
    private String host;

    @Value("${kamis.key}")
    private String key;

    @Value("${kamis.id}")
    private String id;

    @Scheduled(cron = "0 0 0/3 * * *", zone = "Asia/Seoul")
    public void getApi() throws ParseException {
        log.info("농산물 Api 호출");
        priceInfo();
    }

    public List<ItemPriceResponse> priceInfo() throws ParseException {

        String url = host + "action=dailySalesList&p_cert_key=" + key + "&p_cert_id=" + id + "&p_returntype=json";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String priceResult = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject priceJson = (JSONObject) jsonParser.parse(priceResult);
        JSONArray priceArr = (JSONArray) priceJson.get("price");

        List<ItemPriceResponse> prices = new ArrayList<>();

        for (Object price : priceArr) {
            priceJson = (JSONObject) price;

            String trendUrl = host + "action=monthlyPriceTrendList&p_productno=" + priceJson.get("productno").toString()
                    + "&p_regday=" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    + "&p_cert_key=" + key + "&p_cert_id=" + id + "&p_returntype=json";

            String trendResult = restTemplate.exchange(trendUrl, HttpMethod.GET, entity, String.class).getBody();

            JSONObject trendJson = (JSONObject) jsonParser.parse(trendResult);
            JSONArray trendArr = (JSONArray) trendJson.get("price");

            List<ItemTrendResponse> trends = new ArrayList<>();

            for (Object trend : trendArr) {
                trendJson = (JSONObject) trend;

                ItemTrendResponse itemTrendResponse = ItemTrendResponse.builder()
                        .date(trendJson.get("yyyymm").toString())
                        .max(trendJson.get("max").toString())
                        .min(trendJson.get("min").toString())
                        .build();

                trends.add(itemTrendResponse);
            }

            ItemPriceResponse itemPriceResponse = ItemPriceResponse.builder()
                    .productClsName(priceJson.get("product_cls_name").toString())
                    .categoryCode(priceJson.get("category_code").toString())
                    .productNo(priceJson.get("productno").toString())
                    .lastestDay(priceJson.get("lastest_day").toString())
                    .productName(priceJson.get("productName").toString())
                    .unit(priceJson.get("unit").toString())
                    .dpr1(priceJson.get("dpr1").toString())
                    .dpr2(priceJson.get("dpr2").toString())
                    .dpr3(priceJson.get("dpr3").toString())
                    .dpr4(priceJson.get("dpr4").toString())
                    .direction(priceJson.get("direction").toString())
                    .value(priceJson.get("value").toString())
                    .trend(trends)
                    .build();

            prices.add(itemPriceResponse);
        }

        return prices;
    }
}
