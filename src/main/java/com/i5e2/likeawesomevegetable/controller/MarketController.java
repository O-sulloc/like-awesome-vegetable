package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.market.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
@Slf4j
public class MarketController {
    private final AuctionService auctionService;
    private final BuyingService buyingService;
    private final ImgUploadService imgUploadService;

    @GetMapping("/auction")
    public String writeAuctionFrom(Model model, AuctionRequest auctionRequest) {
        return "farmer/farmer-gather-writeform";
    }

    @PostMapping("/auction")
    public String add(@ModelAttribute("auctionRequest") AuctionRequest auctionRequest, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "farmer/farmer-gather-writeform";
        }
        auctionService.creatAuction(auctionRequest);

        for (MultipartFile img : auctionRequest.getUploadImages()){
            System.out.println(img.getOriginalFilename());
            imgUploadService.farmUploadImg(img);
        }

        return "farmer/farmer-detail";
    }

    @GetMapping("/buying")
    public String writeBuyingForm(BuyingRequest buyingRequest) {

        return "company/company-gather-writeform";
    }

    @PostMapping("/buying")
    public String add(BuyingRequest buyingRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "company/company-gather-writeform";
        }
        buyingService.creatBuying(buyingRequest);


        return "company/company-detail";
    }

    //이미지업로드

    @PostMapping("/farm/img")
    @ResponseBody
    public ResponseEntity<Result<FarmAuctionImageResponse>> farmImgUpload(@RequestPart("file") MultipartFile img) throws IOException {
        log.info(img.getOriginalFilename() + "업로드 완료");

        FarmAuctionImageResponse farmAuctionImageResponse = imgUploadService.farmUploadImg(img);


        return ResponseEntity.ok().body(Result.success(farmAuctionImageResponse));
    }

//    @PostMapping("/company/img")
//    @ResponseBody
//    public ResponseEntity<Result<CompanyBuyingResponse>> companyImgUpload(@RequestPart("file") MultipartFile img) throws IOException {
//        log.info(img.getOriginalFilename() + "업로드 완료");
//
//        CompanyBuyingResponse companyBuyingResponse = imgUploadService.companyUploadImg(img);
//
//        return ResponseEntity.ok().body(Result.success(companyBuyingResponse));
//    }


    //    @GetMapping("/img")
//    public String viewfile() {
//        return "company/smart";
//    }
}