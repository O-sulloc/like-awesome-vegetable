package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingDetailResponse;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuctionDetailResponse;
import com.i5e2.likeawesomevegetable.domain.market.MarketDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/market")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MarketDetailController {

    private final MarketDetailService marketDetailService;

    @GetMapping("/buying/{buyingId}")
    public ResponseEntity<Result<CompanyBuyingDetailResponse>> getBuyingDetail(@PathVariable Long buyingId) {
        CompanyBuyingDetailResponse detailResponse = marketDetailService.getGatherDetail(buyingId);

        return ResponseEntity.ok().body(Result.success(detailResponse));
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<Result<FarmAuctionDetailResponse>> getAuctionDetail(@PathVariable Long auctionId) {
        FarmAuctionDetailResponse detailResponse = marketDetailService.getAuctionDetail(auctionId);

        return ResponseEntity.ok().body(Result.success(detailResponse));
    }

}
