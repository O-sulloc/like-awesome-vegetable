package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.like.LikeService;
import com.i5e2.likeawesomevegetable.domain.market.like.dto.LikeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api("Like Controller")
@RequestMapping("/api/v1")
public class LikeController {
    private final LikeService likeService;

    @ApiOperation(
            value = "농가 경매 게시글 좋아요",
            notes = "농가 경매 게시글 ID를 통해 농가 경매 게시글에 좋아요를 남긴다")
    @PostMapping("/auction/{auctionId}/like")
    public ResponseEntity<Result<LikeResponse>> auctionPostLike(@PathVariable("auctionId") Long auctionId,
                                                                Authentication authentication) {
        Result<LikeResponse> auctionLikeResponse = likeService.auctionPostLike(authentication.getName(), auctionId);
        return ResponseEntity.ok().body(auctionLikeResponse);
    }

    @ApiOperation(
            value = "기업 모집 게시글 좋아요",
            notes = "기업 모집 게시글 ID를 통해 기업 모집 게시글에 좋아요를 남긴다")
    @PostMapping("/buying/{buyingId}/like")
    public ResponseEntity<Result<LikeResponse>> buyingPostLike(@PathVariable("buyingId") Long buyingId,
                                                               Authentication authentication) {
        Result<LikeResponse> auctionLikeResponse = likeService.buyingPostLike(authentication.getName(), buyingId);
        return ResponseEntity.ok().body(auctionLikeResponse);
    }

}
