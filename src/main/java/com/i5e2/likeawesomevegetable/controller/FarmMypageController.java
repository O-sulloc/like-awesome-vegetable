package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.FarmMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
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

@Api("Farm-User MyPage Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage/farm")
public class FarmMypageController {
    private final FarmMypageService farmMypageService;

    @ApiOperation(value = "특정 농가 사용자가 작성한 경매글 조회(최신순)"
            , notes = "사용자 인증 이메일을 통해 작성한 경매글을 최신순으로 조회한다.")
    @GetMapping("/auction")
    public ResponseEntity<Result> readFarmAuctionPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmAuctionByUser> farmAuctionByUser = farmMypageService.readFarmActionByUser(pageable, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmAuctionByUser));
    }

    @ApiOperation(value = "특정 농가 사용자의 모집 참여 내역 조회(최신순)"
            , notes = "사용자 인증 이메일을 통해 모집 참여 내역을 최신순으로 조회한다")
    @GetMapping("/apply")
    public ResponseEntity<Result> readFarmApplyPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmApplyByUser> farmApplyByUser = farmMypageService.readFarmApplyPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(farmApplyByUser));
    }

}
