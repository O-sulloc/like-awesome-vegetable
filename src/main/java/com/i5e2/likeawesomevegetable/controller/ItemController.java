package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.item.ItemLowestPriceResponse;
import com.i5e2.likeawesomevegetable.domain.item.ItemPriceResponse;
import com.i5e2.likeawesomevegetable.domain.item.ItemService;
import com.i5e2.likeawesomevegetable.domain.item.RegionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("ItemController")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    @ApiOperation(value = "농산물 테이블 생성", notes="농산물 품목 코드표에 따른 농산물 테이블을 생성한다.")
    @PostMapping("/item/create")
    public ResponseEntity<String> create() {

        itemService.save();
        return ResponseEntity.ok().body("Create Item Table");
    }


    // 최근일자 도·소매 가격정보
    @ApiOperation(value = "농산물 가격 정보 조회",
            notes="최근일자 도·소매 가격 정보와 월평균 가격 추이를 조회한다.")
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
