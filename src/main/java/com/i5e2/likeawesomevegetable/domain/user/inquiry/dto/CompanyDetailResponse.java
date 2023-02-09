package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailResponse {

    private String companyImage;
    private CompanyUserResponse companyUserResponse;
    private Page<BuyingListResponse> buyingListResponses;
}
