package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.user.inquiry.FarmInquiryService;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.FarmDetailResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.FarmListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/farm")
public class FarmInquiryController {

    private final FarmInquiryService farmInquiryService;

    @GetMapping("/list")
    public ResponseEntity<Page<FarmListResponse>> list(@PageableDefault(size = 15, sort="id") Pageable pageable) {

        return ResponseEntity.ok().body(farmInquiryService.list(pageable));
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmDetailResponse> detail(@PathVariable Long farmId, Pageable pageable) {

        return ResponseEntity.ok().body(farmInquiryService.detail(farmId, pageable));
    }
}
