package com.i5e2.likeawesomevegetable.farm.bidding.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.farm.bidding.dto.BiddingRequest;
import com.i5e2.likeawesomevegetable.farm.bidding.dto.BiddingResponse;
import com.i5e2.likeawesomevegetable.farm.bidding.service.BiddingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api("BiddingController")
@RequestMapping("/api/v1/auction/{farmAuctionId}")
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    // 입찰 조회
    @ApiOperation(value = "경매 입찰 조회", notes = "경매 게시글 id를 통해 입찰 목록을 조회한다")
    @GetMapping("/list")
    public ResponseEntity<Result<Page<BiddingResponse>>> getApply(
            @PathVariable Long farmAuctionId,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BiddingResponse> biddingResponsePage = biddingService.list(farmAuctionId, pageable);
        return ResponseEntity.ok().body(Result.success(biddingResponsePage));
    }

    // 입찰 신청
    @ApiOperation(value = "경매 입찰 신청", notes = "경매 게시글 id를 통해 입찰 신청을 한다")
    @PostMapping("/bidding")
    public ResponseEntity<Result<BiddingResponse>> priceInput(
            @RequestBody BiddingRequest request, @PathVariable Long farmAuctionId, Authentication authentication, HttpSession session) {

        BiddingResponse biddingResponse = biddingService.bidding(request, farmAuctionId, authentication.getName(), session);
        return ResponseEntity.ok().body(Result.success(biddingResponse));
    }
}

