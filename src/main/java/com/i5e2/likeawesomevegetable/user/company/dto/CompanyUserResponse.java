package com.i5e2.likeawesomevegetable.user.company.dto;

import com.i5e2.likeawesomevegetable.user.company.CompanyUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUserResponse {

    private String companyName;
    private String companyPhoneNo;
    private String companyWebsite;
    private String companyOwnerName;
    private String companyBusinessNo;
    private String companyOpenDate;
    private String companyInfo;
    private String companyLineInfo;
    private String companyAddress;
    private LocalDateTime companyRegisteredAt;
    private String companyMajorItem;

    public static CompanyUserResponse fromEntity(CompanyUser companyUser, String item) {
        return CompanyUserResponse.builder()
                .companyName(companyUser.getCompanyName())
                .companyPhoneNo(companyUser.getCompanyPhoneNo())
                .companyWebsite(companyUser.getCompanyWebsite())
                .companyOwnerName(companyUser.getCompanyOwnerName())
                .companyBusinessNo(companyUser.getCompanyBusinessNo())
                .companyOpenDate(companyUser.getCompanyOpenDate())
                .companyInfo(companyUser.getCompanyInfo())
                .companyLineInfo(companyUser.getCompanyLineInfo())
                .companyAddress(companyUser.getCompanyAddress())
                .companyRegisteredAt(companyUser.getCompanyRegisteredAt())
                .companyMajorItem(item)
                .build();
    }
}
