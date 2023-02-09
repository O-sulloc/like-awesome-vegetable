package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.FarmMypageService;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
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
@RequestMapping("/api/v1/mypage/farm")
public class FarmMypageController {
    private final FarmMypageService farmMypageService;

    @GetMapping("/auction")
    public ResponseEntity<Result<List<FarmAuctionByUser>>> readFarmAuctionPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmAuctionByUser> farmAuctionByUser = farmMypageService.readFarmActionByUser(pageable, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmAuctionByUser));
    }

    @GetMapping("/apply")
    public ResponseEntity<Result<List<FarmApplyByUser>>> readFarmApplyPosts(Authentication authentication
            , @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<FarmApplyByUser> farmApplyByUser = farmMypageService.readFarmApplyPosts(authentication.getName(), pageable);
        return ResponseEntity.ok().body(Result.success(farmApplyByUser));
    }

}
