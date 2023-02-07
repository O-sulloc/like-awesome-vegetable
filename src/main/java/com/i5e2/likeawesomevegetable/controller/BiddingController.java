package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.apply.BiddingService;
import com.i5e2.likeawesomevegetable.domain.apply.dto.BiddingRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.BiddingResponse;
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
@RequestMapping("/api/v1/auction/{farmAuctionId}")
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    // 입찰 조회
    @GetMapping("/list")
    public ResponseEntity<Result<Page<BiddingResponse>>> getApply(
            @PathVariable Long farmAuctionId,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BiddingResponse> biddingResponsePage = biddingService.list(farmAuctionId, pageable);
        return ResponseEntity.ok().body(Result.success(biddingResponsePage));
    }

    // 입찰 신청
    @PostMapping("/bidding")
    public ResponseEntity<Result<BiddingResponse>> priceInput(
            @RequestBody BiddingRequest request, @PathVariable Long farmAuctionId, Authentication authentication, HttpSession session) {

        BiddingResponse biddingResponse = biddingService.bidding(request, farmAuctionId, authentication.getName(), session);
        return ResponseEntity.ok().body(Result.success(biddingResponse));
    }
}

