package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketRestController {

    private final AuctionService auctionService;
    private final BuyingService buyingService;
    private final ImgUploadService imgUploadService;

    @PostMapping("/auction")
    public ResponseEntity<Result<AuctionResponse>> create(@RequestBody AuctionRequest auctionRequest, Authentication authentication) throws IOException {
        AuctionResponse auctionResponse = auctionService.createAuctionResponse(auctionRequest,authentication.getName());
        Result<AuctionResponse> response = new Result<>("SUCCESS",auctionResponse);

        FarmAuction farmAuction = auctionService.creatAuction(auctionRequest, authentication.getName());
        for (MultipartFile img : auctionRequest.getUploadImages()) {
            System.out.println(img.getOriginalFilename());
            imgUploadService.farmUploadImg(img, farmAuction);
        }

        return ResponseEntity.ok().body(response);
    }
}
