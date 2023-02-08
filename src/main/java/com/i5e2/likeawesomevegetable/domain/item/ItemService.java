package com.i5e2.likeawesomevegetable.domain.item;

import com.i5e2.likeawesomevegetable.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    private final ItemJpaRepository itemJpaRepository;

    @Value("${kamis.host}")
    private String host;

    @Value("${kamis.key}")
    private String key;

    @Value("${kamis.id}")
    private String id;

    public void save() {
        itemJpaRepository.truncateItem();

        itemJpaRepository.save(new Item("111", "쌀", "100", "식량작물"));
        itemJpaRepository.save(new Item("112", "찹쌀", "100", "식량작물"));
        itemJpaRepository.save(new Item("141", "콩", "100", "식량작물"));
        itemJpaRepository.save(new Item("142", "팥", "100", "식량작물"));
        itemJpaRepository.save(new Item("143", "녹두", "100", "식량작물"));
        itemJpaRepository.save(new Item("144", "메밀", "100", "식량작물"));
        itemJpaRepository.save(new Item("151", "고구마", "100", "식량작물"));
        itemJpaRepository.save(new Item("152", "감자", "100", "식량작물"));
        itemJpaRepository.save(new Item("211", "배추", "200", "채소류"));
        itemJpaRepository.save(new Item("212", "양배추", "200", "채소류"));
        itemJpaRepository.save(new Item("213", "시금치", "200", "채소류"));
        itemJpaRepository.save(new Item("214", "상추", "200", "채소류"));
        itemJpaRepository.save(new Item("215", "얼갈이배추", "200", "채소류"));
        itemJpaRepository.save(new Item("216", "갓", "200", "채소류"));
        itemJpaRepository.save(new Item("221", "수박", "200", "채소류"));
        itemJpaRepository.save(new Item("222", "참외", "200", "채소류"));
        itemJpaRepository.save(new Item("223", "오이", "200", "채소류"));
        itemJpaRepository.save(new Item("224", "호박", "200", "채소류"));
        itemJpaRepository.save(new Item("225", "토마토", "200", "채소류"));
        itemJpaRepository.save(new Item("226", "딸기", "200", "채소류"));
        itemJpaRepository.save(new Item("231", "무", "200", "채소류"));
        itemJpaRepository.save(new Item("232", "당근", "200", "채소류"));
        itemJpaRepository.save(new Item("233", "열무", "200", "채소류"));
        itemJpaRepository.save(new Item("241", "건고추", "200", "채소류"));
        itemJpaRepository.save(new Item("242", "풋고추", "200", "채소류"));
        itemJpaRepository.save(new Item("243", "붉은고추", "200", "채소류"));
        itemJpaRepository.save(new Item("244", "피마늘", "200", "채소류"));
        itemJpaRepository.save(new Item("245", "양파", "200", "채소류"));
        itemJpaRepository.save(new Item("246", "파", "200", "채소류"));
        itemJpaRepository.save(new Item("247", "생강", "200", "채소류"));
        itemJpaRepository.save(new Item("248", "고춧가루", "200", "채소류"));
        itemJpaRepository.save(new Item("251", "가지", "200", "채소류"));
        itemJpaRepository.save(new Item("252", "미나리", "200", "채소류"));
        itemJpaRepository.save(new Item("253", "깻잎", "200", "채소류"));
        itemJpaRepository.save(new Item("254", "부추", "200", "채소류"));
        itemJpaRepository.save(new Item("255", "피망", "200", "채소류"));
        itemJpaRepository.save(new Item("256", "파프리카", "200", "채소류"));
        itemJpaRepository.save(new Item("257", "멜론", "200", "채소류"));
        itemJpaRepository.save(new Item("258", "깐마늘(국산)", "200", "채소류"));
        itemJpaRepository.save(new Item("259", "깐마늘(수입)", "200", "채소류"));
        itemJpaRepository.save(new Item("312", "참깨", "300", "특용작물"));
        itemJpaRepository.save(new Item("313", "들깨", "300", "특용작물"));
        itemJpaRepository.save(new Item("314", "땅콩", "300", "특용작물"));
        itemJpaRepository.save(new Item("315", "느타리버섯", "300", "특용작물"));
        itemJpaRepository.save(new Item("316", "팽이버섯", "300", "특용작물"));
        itemJpaRepository.save(new Item("317", "새송이버섯", "300", "특용작물"));
        itemJpaRepository.save(new Item("318", "호두", "300", "특용작물"));
        itemJpaRepository.save(new Item("319", "아몬드", "300", "특용작물"));
        itemJpaRepository.save(new Item("411", "사과", "400", "과일류"));
        itemJpaRepository.save(new Item("412", "배", "400", "과일류"));
        itemJpaRepository.save(new Item("413", "복숭아", "400", "과일류"));
        itemJpaRepository.save(new Item("414", "포도", "400", "과일류"));
        itemJpaRepository.save(new Item("415", "감귤", "400", "과일류"));
        itemJpaRepository.save(new Item("416", "단감", "400", "과일류"));
        itemJpaRepository.save(new Item("418", "바나나", "400", "과일류"));
        itemJpaRepository.save(new Item("419", "참다래", "400", "과일류"));
        itemJpaRepository.save(new Item("420", "파인애플", "400", "과일류"));
        itemJpaRepository.save(new Item("421", "오렌지", "400", "과일류"));
        itemJpaRepository.save(new Item("422", "방울토마토", "200", "채소류"));
        itemJpaRepository.save(new Item("423", "자몽", "400", "과일류"));
        itemJpaRepository.save(new Item("424", "레몬", "400", "과일류"));
        itemJpaRepository.save(new Item("425", "체리", "400", "과일류"));
        itemJpaRepository.save(new Item("426", "건포도", "400", "과일류"));
        itemJpaRepository.save(new Item("427", "건블루베리", "400", "과일류"));
        itemJpaRepository.save(new Item("428", "망고", "400", "과일류"));
        itemJpaRepository.save(new Item("512", "쇠고기", "500", "축산물"));
        itemJpaRepository.save(new Item("514", "돼지고기", "500", "축산물"));
        itemJpaRepository.save(new Item("515", "닭고기", "500", "축산물"));
        itemJpaRepository.save(new Item("516", "계란", "500", "축산물"));
        itemJpaRepository.save(new Item("535", "우유", "500", "축산물"));
        itemJpaRepository.save(new Item("611", "고등어", "600", "수산물"));
        itemJpaRepository.save(new Item("612", "꽁치", "600", "수산물"));
        itemJpaRepository.save(new Item("613", "갈치", "600", "수산물"));
        itemJpaRepository.save(new Item("615", "명태", "600", "수산물"));
        itemJpaRepository.save(new Item("619", "물오징어", "600", "수산물"));
        itemJpaRepository.save(new Item("638", "건멸치", "600", "수산물"));
        itemJpaRepository.save(new Item("639", "북어", "600", "수산물"));
        itemJpaRepository.save(new Item("640", "건오징어", "600", "수산물"));
        itemJpaRepository.save(new Item("641", "김", "600", "수산물"));
        itemJpaRepository.save(new Item("642", "건미역", "600", "수산물"));
        itemJpaRepository.save(new Item("644", "굴", "600", "수산물"));
        itemJpaRepository.save(new Item("649", "수입조기", "600", "수산물"));
        itemJpaRepository.save(new Item("650", "새우젓", "600", "수산물"));
        itemJpaRepository.save(new Item("651", "멸치액젓", "600", "수산물"));
        itemJpaRepository.save(new Item("652", "굵은소금", "600", "수산물"));
        itemJpaRepository.save(new Item("653", "전복", "600", "수산물"));
        itemJpaRepository.save(new Item("654", "새우", "600", "수산물"));
    }

    @Scheduled(cron = "0 0 0/3 * * *", zone = "Asia/Seoul")
    public void getApi() throws ParseException, InterruptedException {
        log.info("농산물 Api 호출");
        Thread.sleep(1000);
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
