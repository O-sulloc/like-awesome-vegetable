package com.i5e2.likeawesomevegetable.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPriceResponse {

    private String productClsName;  // 구분 이름
    private String categoryCode;    // 부류코드
    private String productNo;       // 품목코드
    private String lastestDay;      // 최근조사일
    private String productName;     // 품목명
    private String unit;            // 단위
    private String dpr1;            // 최근 조사일자 가격
    private String dpr2;            // 1일전 가격
    private String dpr3;            // 1개월전 가격
    private String dpr4;            // 1년전 가격
    private String direction;       // 등락여부 (0:가격하락 1:가격상승 2:등락없음)
    private String value;           // 등락율
    // 월별 가격 추이
    private List<ItemTrendResponse> trend;
}
