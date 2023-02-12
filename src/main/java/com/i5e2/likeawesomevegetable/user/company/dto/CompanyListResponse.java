package com.i5e2.likeawesomevegetable.user.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyListResponse {

    private Long id;
    private String companyName;
    private String companyMajorItem;
    private String companyAddress;
}
