package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketRestController {

    private final AuctionService auctionService;
    private final BuyingService buyingService;

    @PostMapping("/auction")
    public ResponseEntity<Result<AuctionResponse>> createAuction(@RequestBody AuctionRequest auctionRequest, Authentication authentication) throws IOException {
        AuctionResponse auctionResponse = auctionService.createAuction(auctionRequest, authentication.getName());
        Result<AuctionResponse> response = new Result<>("SUCCESS", auctionResponse);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/buying")
    public ResponseEntity<Result<BuyingResponse>> createBuying(@RequestBody BuyingRequest buyingRequest, Authentication authentication) {
        BuyingResponse buyingResponse = buyingService.creatBuying(buyingRequest, authentication.getName());
        Result<BuyingResponse> response = new Result<>("SUCCESS", buyingResponse);

        return ResponseEntity.ok().body(response);
    }

}
