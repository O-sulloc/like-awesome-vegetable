package com.i5e2.likeawesomevegetable.domain.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyFarmUserRequest {
    private String farmOwnerName;
    private String farmMajorItem;
    private String farmType;
    private String groundArea;
    private String facilityArea;
    private String farmWebsite;
    private String farmInfo;
    private String farmName;
    private String farmAddress;
}
