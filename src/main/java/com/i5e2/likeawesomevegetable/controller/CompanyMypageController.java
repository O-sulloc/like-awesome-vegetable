package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.CompanyMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBuyingByUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class CompanyMypageController {

    private final CompanyMypageService companyMypageService;

    @GetMapping("/company/buying")
    public ResponseEntity<Result> readCompanyBuyingPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBuyingByUser> farmAuctionByUsers = companyMypageService.readCompanyBuyingPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(farmAuctionByUsers));
    }

    @GetMapping("/company/bidding")
    public ResponseEntity<Result> readCompanyBiddingPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBiddingByUser> companyBiddingByUsers = companyMypageService.readCompanyBiddingPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(companyBiddingByUsers));
    }

}
