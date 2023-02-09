package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmUserResponse {

    private String farmOwnerName;
    private String farmMajorItem;
    private String farmType;
    private String groundArea;
    private String facilityArea;
    private String farmWebsite;
    private String farmInfo;
    private String farmName;
    private String farmAddress;
    private String farmRegisteredAt;

    public static FarmUserResponse fromEntity(FarmUser farmUser, String item) {
        return FarmUserResponse.builder()
                .farmOwnerName(farmUser.getFarmOwnerName())
                .farmMajorItem(item)
                .farmType(farmUser.getFarmType())
                .groundArea(farmUser.getGroundArea())
                .facilityArea(farmUser.getFacilityArea())
                .farmWebsite(farmUser.getFarmWebsite())
                .farmInfo(farmUser.getFarmInfo())
                .farmName(farmUser.getFarmName())
                .farmAddress(farmUser.getFarmAddress())
                .farmRegisteredAt(farmUser.getFarmAddress())
                .build();
    }
}
