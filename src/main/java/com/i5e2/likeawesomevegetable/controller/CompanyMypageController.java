package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.CompanyMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBuyingByUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api("Company-User MyPage Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class CompanyMypageController {

    private final CompanyMypageService companyMypageService;

    @ApiOperation(value = "특정 기업 사용자가 작성한 모집글 조회(최신순)"
            , notes = "사용자 인증 이메일을 통해 작성한 글을 최신순으로 조회한다.")
    @GetMapping("/company/buying")
    public ResponseEntity<Result> readCompanyBuyingPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBuyingByUser> farmAuctionByUsers = companyMypageService.readCompanyBuyingPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(farmAuctionByUsers));
    }

    @ApiOperation(value = "특정 기업 사용자의 경매 입찰 내역(최신순)"
            , notes = "사용자 인증 이메일을 통해 경매 입찰 내역을 최신순으로 조회한다.")
    @GetMapping("/company/bidding")
    public ResponseEntity<Result> readCompanyBiddingPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CompanyBiddingByUser> companyBiddingByUsers = companyMypageService.readCompanyBiddingPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(companyBiddingByUsers));
    }

}
