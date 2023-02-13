package com.i5e2.likeawesomevegetable.board.post.controller;

import com.i5e2.likeawesomevegetable.board.post.service.MarketDetailService;
import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.company.buying.dto.CompanyBuyingDetailResponse;
import com.i5e2.likeawesomevegetable.farm.auction.dto.FarmAuctionDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Post Read Controller")
@RequestMapping("api/v1/market")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MarketDetailController {

    private final MarketDetailService marketDetailService;

    @ApiOperation(value = "경매 게시글 조회", notes = "기업이 등록한 모집 게시글 한 건을 조회한다.")
    @GetMapping("/buying/{buyingId}")
    public ResponseEntity<Result<CompanyBuyingDetailResponse>> getBuyingDetail(@PathVariable Long buyingId) {
        CompanyBuyingDetailResponse detailResponse = marketDetailService.getGatherDetail(buyingId);

        return ResponseEntity.ok().body(Result.success(detailResponse));
    }

    @ApiOperation(value = "경매 게시글 조회", notes = "농가가 등록한 경매 게시글 한 건을 조회한다.")
    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<Result<FarmAuctionDetailResponse>> getAuctionDetail(@PathVariable Long auctionId) {
        FarmAuctionDetailResponse detailResponse = marketDetailService.getAuctionDetail(auctionId);

        return ResponseEntity.ok().body(Result.success(detailResponse));
    }

}
