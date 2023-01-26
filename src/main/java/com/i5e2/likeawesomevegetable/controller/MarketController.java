package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.market.AuctionRequest;
import com.i5e2.likeawesomevegetable.domain.market.AuctionService;
import com.i5e2.likeawesomevegetable.domain.market.BuyingRequest;
import com.i5e2.likeawesomevegetable.domain.market.BuyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {

    /*
1. 글쓰기 버튼 누르면 글쓰기 폼으로 이동 @get
2. 작성완료 누르면 post로 보내기 form action post
3. post로 받은거 리스트페이지로 리턴  @Post 리턴 -> 리스트view
 */
    private final AuctionService auctionService;
    private final BuyingService buyingService;
    @PostMapping("/auction")
    public void add(@Valid @RequestBody AuctionRequest auctionRequest){
        auctionService.creatAuction(auctionRequest);
    }


    @GetMapping("/buying")
    public String writeForm(BuyingRequest buyingRequest){

        return "/company/company-gather-writeform";
    }
    @PostMapping("/buying")
    public String add(@Valid BuyingRequest buyingRequest, BindingResult result){
        if(result.hasErrors()){
            return "/company/company-gather-writeform";
        }

        buyingService.creatBuying(buyingRequest);
        return "/company/company-detail";
    }
}
