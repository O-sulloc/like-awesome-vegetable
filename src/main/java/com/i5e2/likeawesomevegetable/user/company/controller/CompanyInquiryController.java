package com.i5e2.likeawesomevegetable.user.company.controller;

import com.i5e2.likeawesomevegetable.user.company.dto.CompanyDetailResponse;
import com.i5e2.likeawesomevegetable.user.company.dto.CompanyListResponse;
import com.i5e2.likeawesomevegetable.user.company.service.CompanyInquiryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("CompanyInquiryController")
@RequiredArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyInquiryController {

    private final CompanyInquiryService companyInquiryService;

    @ApiOperation(value = "기업 사용자 조회", notes = "정회원 등록을 한 기업 사용자를 조회한다.")
    @GetMapping("/list")
    public ResponseEntity<Page<CompanyListResponse>> list(@PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok().body(companyInquiryService.list(pageable));
    }

    @ApiOperation(value = "기업 사용자 상세 조회", notes = "기업 사용자 id를 통해 상내 내역을 조회한다.")
    @GetMapping("{companyId}")
    public ResponseEntity<CompanyDetailResponse> detail(@PathVariable Long companyId, Pageable pageable) {

        return ResponseEntity.ok().body(companyInquiryService.detail(companyId, pageable));
    }
}