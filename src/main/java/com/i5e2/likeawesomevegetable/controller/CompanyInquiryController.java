package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.user.inquiry.CompanyInquiryService;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.CompanyDetailResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.CompanyListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyInquiryController {

    private final CompanyInquiryService companyInquiryService;

    @GetMapping("/list")
    public ResponseEntity<Page<CompanyListResponse>> list(@PageableDefault(size = 10, sort="id") Pageable pageable) {

        return ResponseEntity.ok().body(companyInquiryService.list(pageable));
    }

    @GetMapping("{companyId}")
    public ResponseEntity<CompanyDetailResponse> detail(@PathVariable Long companyId, Pageable pageable) {

        return ResponseEntity.ok().body(companyInquiryService.detail(companyId, pageable));
    }
}