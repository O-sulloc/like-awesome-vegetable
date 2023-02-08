package com.i5e2.likeawesomevegetable.domain.market.post;

import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionListResponse {
    private Long id;
    private String farmOwnerName;
    private String auctionTitle;
    private String auctionStartTime;
    private String auctionEndTime;
    private int auctionItemCategory;
    private String auctionItem;
    private Integer auctionQuantity;
    private Long auctionHighestPrice;
    private String auctionTag;
    private PostPointActivateEnum postPointActivate;
}
