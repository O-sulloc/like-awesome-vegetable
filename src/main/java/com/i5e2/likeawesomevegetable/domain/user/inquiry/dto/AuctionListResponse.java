package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionListResponse {

    private String auctionTitle;
    private String auctionItemCategory;
    private String auctionItem;
    private String auctionStartTime;
    private String auctionEndTime;

    public static AuctionListResponse fromEntity(FarmAuction farmAuction, String category, String item) {
        return AuctionListResponse.builder()
                .auctionTitle(farmAuction.getAuctionTitle())
                .auctionItemCategory(category)
                .auctionItem(item)
                .auctionStartTime(farmAuction.getAuctionStartTime())
                .auctionEndTime(farmAuction.getAuctionEndTime())
                .build();
    }
}
