package com.i5e2.likeawesomevegetable.domain.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCompanyUserRequest {
    private String companyName;
    private String companyPhoneNo;
    private String companyWebsite;
    private String companyOwnerName;
    private String companyBusinessNo;
    private String companyOpenDate;
    private String companyInfo;
    private String companyLineInfo;
    private Integer companyMajorItem;
}