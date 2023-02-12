package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.post.AuctionListResponse;
import com.i5e2.likeawesomevegetable.domain.market.post.BuyingListResponse;
import com.i5e2.likeawesomevegetable.domain.market.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Auction/Buying Post List Controller")
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    /*-- 모집 및 경매 오픈시간 순 --*/
    @ApiOperation(value = "모집게시글 리스트(모집오픈순)", notes = "모집게시글을 모집오픈시간순으로 정렬해 리스트로 보여준다.")
    @GetMapping("/buying/list")
    public ResponseEntity<Result<Page<BuyingListResponse>>> getBuytingList(
            @PageableDefault(size = 20, sort = "buying_start_time", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BuyingListResponse> buyingListResponse = postService.getBuyingList(pageable);
        return ResponseEntity.ok().body(Result.success(buyingListResponse));
    }
    @ApiOperation(value = "경매게시글 리스트(경매오픈순)", notes = "경매게시글을 경매오픈시간순으로 정렬해 리스트로 보여준다.")
    @GetMapping("/auction/list")
    public ResponseEntity<Result<Page<AuctionListResponse>>> getAuctionList(
            @PageableDefault(size = 20, sort = "auction_start_time", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AuctionListResponse> auctionListResponses = postService.getAuctionList(pageable);
        return ResponseEntity.ok().body(Result.success(auctionListResponses));
    }

    /*-- 모집 및 경매 인기순 --*/
    @ApiOperation(value = "모집게시글 리스트(인기순)", notes = "모집게시글을 인기순(좋아요 개수)으로 정렬해 리스트로 보여준다.")
    @GetMapping("/buying/list-hot")
    public ResponseEntity<Result<Page<BuyingListResponse>>> getHotBuytingList(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BuyingListResponse> buyingListResponse = postService.getHotBuytingList(pageable);
        return ResponseEntity.ok().body(Result.success(buyingListResponse));
    }
    @ApiOperation(value = "경매게시글 리스트(인기순)", notes = "경매게시글을 인기순(좋아요 개수)으로 정렬해 리스트로 보여준다.")
    @GetMapping("/auction/list-hot")
    public ResponseEntity<Result<Page<AuctionListResponse>>> getHotAuctionList(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<AuctionListResponse> auctionListResponses = postService.getHotAuctionList(pageable);
        return ResponseEntity.ok().body(Result.success(auctionListResponses));
    }
}
