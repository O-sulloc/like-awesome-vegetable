package com.i5e2.likeawesomevegetable.domain.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmAuctionDetailResponse {
    private Long id;
    private String auctionTitle;
    private String auctionDescription;
    private String auctionStartTime;
    private String auctionEndTime;
    private String auctionItem;
    private String auctionItemCategory;
    private Integer auctionStartPrice;
    private Integer auctionLimitPrice;
    private Integer auctionHighestPrice;
    private Integer auctionQuantity;
    private String auctionShipping;
    private Long farmUserId;
    private ParticipationStatus auctionStatus;
    private PostPointActivateEnum postPointActivate;
    private String auctionRegisteredAt;
    private String auctionModifiedAt;
    private String auctionDeletedAt;

    private List<ImgFindListResponse> images;
}
