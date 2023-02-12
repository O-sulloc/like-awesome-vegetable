package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Api("MarketRestController")
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
@Slf4j
public class MarketRestController {

    private final AuctionService auctionService;
    private final BuyingService buyingService;

    @ApiOperation(value = "농가 경매글 작성", notes = "농가 인증을 받은 사용자가 경매 글을 작성한다.")
    @PostMapping("/auction")
    public ResponseEntity<Result<AuctionResponse>> createAuction(@Valid @RequestPart(value = "dto") AuctionRequest dto,
                                                                 @RequestPart(value = "imgs") List<MultipartFile> imgs,
                                                                 Authentication authentication) throws IOException {

    AuctionResponse auctionResponse = auctionService.createAuction(dto,imgs,authentication.getName());
    Result<AuctionResponse> response = new Result<>("SUCCESS", auctionResponse);

        return ResponseEntity.ok().body(response);
}
    @ApiOperation(value = "기업 모집글 작성", notes = "기업 인증을 받은 사용자가  모집 글을 작성한다.")
    @PostMapping("/buying")
    public ResponseEntity<Result<BuyingResponse>> createBuying(@Valid @RequestBody BuyingRequest buyingRequest, Authentication authentication) {
        BuyingResponse buyingResponse = buyingService.creatBuying(buyingRequest, authentication.getName());
        Result<BuyingResponse> response = new Result<>("SUCCESS", buyingResponse);

        return ResponseEntity.ok().body(response);
    }

    // 모집 게시글 종료
    @ApiOperation(value = "모집 종료",
            notes="모집이 종료되면 모집 게시글 id를 통해 모집 게시글 상태와 그 게시글의 참여 상태를 모두 END로 변경")
    @PostMapping("/buying/{companyBuyingId}/end")
    public ResponseEntity<Result<String>> applyEnd(@PathVariable Long companyBuyingId) {

        buyingService.applyEnd(companyBuyingId);
        return ResponseEntity.ok().body(Result.success("모집 종료"));
    }
}
