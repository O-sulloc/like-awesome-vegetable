package com.i5e2.likeawesomevegetable.domain.apply.dto;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyResponse {

    private Long supplyQuantity;
    private Long companyBuyingId;
    private Long userId;

    public static ApplyResponse fromEntity(Apply savedApply) {
        return ApplyResponse.builder()
                .supplyQuantity(savedApply.getSupplyQuantity())
                .companyBuyingId(savedApply.getCompanyBuying().getId())
                .userId(savedApply.getUser().getId())
                .build();
    }
}