package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.FarmMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class FarmMypageController {
    private final FarmMypageService farmMypageService;

    @GetMapping("/farm/auction/{id}")
    public ResponseEntity<Result<List<FarmAuctionByUser>>> readFarmAuctionPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmAuctionByUser> farmAuctionByUser = farmMypageService.readFarmActionByUser(pageable, userId);
        return ResponseEntity.ok().body(Result.success(farmAuctionByUser));
    }

    @GetMapping("/farm/apply/{id}")
    public ResponseEntity<Result<List<FarmApplyByUser>>> readFarmApplyPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmApplyByUser> farmApplyByUser = farmMypageService.readFarmApplyPosts(userId, pageable);
        return ResponseEntity.ok().body(Result.success(farmApplyByUser));
    }

    @GetMapping("/company/buying/{id}")
    public ResponseEntity<Result<List<FarmAuctionByUser>>> readCompanyBuyingPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<FarmAuctionByUser> farmAuctionByUsers = farmMypageService.readCompanyBuyingPosts(userId, pageable);
        return ResponseEntity.ok().body(Result.success(farmAuctionByUsers));
    }

    @GetMapping("/company/bidding/{id}")
    public ResponseEntity<Result<List<CompanyBiddingByUser>>> readCompanyBiddingPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBiddingByUser> companyBiddingByUsers = farmMypageService.readCompanyBiddingPosts(userId, pageable);
        return ResponseEntity.ok().body(Result.success(companyBiddingByUsers));
    }


}
