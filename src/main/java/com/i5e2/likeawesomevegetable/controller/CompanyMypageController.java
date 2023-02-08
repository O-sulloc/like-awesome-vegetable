package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.CompanyMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
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
public class CompanyMypageController {

    private final CompanyMypageService companyMypageService;

    @GetMapping("/company/buying/{id}")
    public ResponseEntity<Result<List<FarmAuctionByUser>>> readCompanyBuyingPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<FarmAuctionByUser> farmAuctionByUsers = companyMypageService.readCompanyBuyingPosts(userId, pageable);
        return ResponseEntity.ok().body(Result.success(farmAuctionByUsers));
    }

    @GetMapping("/company/bidding/{id}")
    public ResponseEntity<Result<List<CompanyBiddingByUser>>> readCompanyBiddingPosts(@PathVariable("id") Long userId
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBiddingByUser> companyBiddingByUsers = companyMypageService.readCompanyBiddingPosts(userId, pageable);
        return ResponseEntity.ok().body(Result.success(companyBiddingByUsers));
    }

}
