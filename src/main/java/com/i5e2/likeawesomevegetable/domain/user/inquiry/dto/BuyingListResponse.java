package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyingListResponse {

    private String buyingTitle;
    private String buyingItemCategory;
    private String buyingItem;
    private String buyingStartTime;
    private String buyingEndTime;

    public static BuyingListResponse fromEntity(CompanyBuying companyBuying, String category, String item) {
        return BuyingListResponse.builder()
                .buyingTitle(companyBuying.getBuyingTitle())
                .buyingItemCategory(category)
                .buyingItem(item)
                .buyingStartTime(companyBuying.getBuyingStartTime())
                .buyingEndTime(companyBuying.getBuyingEndTime())
                .build();
    }
}
