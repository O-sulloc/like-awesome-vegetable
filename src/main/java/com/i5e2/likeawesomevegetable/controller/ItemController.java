package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.item.ItemLowestPriceResponse;
import com.i5e2.likeawesomevegetable.domain.item.ItemPriceResponse;
import com.i5e2.likeawesomevegetable.domain.item.ItemService;
import com.i5e2.likeawesomevegetable.domain.item.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item/create")
    public ResponseEntity<String> create() {

        itemService.save();
        return ResponseEntity.ok().body("Create Item Table");
    }


    // 최근일자 도·소매 가격정보
    @GetMapping("/item")
    public ResponseEntity<List<ItemPriceResponse>> priceInfo() throws ParseException {

        return ResponseEntity.ok().body(itemService.priceInfo());
    }

    @GetMapping("/item/lowest/{item-code}")
    public ResponseEntity<Result<List<ItemLowestPriceResponse>>> getItemLowestPriceFive(@PathVariable("item-code") String itemCode) {
        Result<List<ItemLowestPriceResponse>> itemLowestPriceFive = itemService.getItemLowestPriceFive(itemCode);
        return ResponseEntity.ok().body(itemLowestPriceFive);
    }

    // 지역별 품목 거래량, 입찰가 통계
    @GetMapping("item/region/{region}")
    public ResponseEntity<Result<RegionResponse>> getRegionAverage(@PathVariable("region") String region) {
        Result<RegionResponse> regionAverage = itemService.getRegionAverage(region);
        return ResponseEntity.ok().body(regionAverage);
    }

}
