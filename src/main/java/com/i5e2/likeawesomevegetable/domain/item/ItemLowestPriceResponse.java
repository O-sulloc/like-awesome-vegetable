package com.i5e2.likeawesomevegetable.domain.item;

import com.i5e2.likeawesomevegetable.domain.market.ParticipationStatus;
import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;

public interface ItemLowestPriceResponse {
    Long getFarmAuctionId();

    Integer getAuctionStartPrice();

    Long getFarmUserId();

    String getAuctionTitle();

    String getAuctionItem();

    String getAuctionItemCategory();

    Integer getAuctionQuantity();

    String getAuctionDescription();

    String getAuctionStartTime();

    String getAuctionEndTime();

    Long getAuctionHighestPrice();

    Integer getAuctionLimitPrice();

    ParticipationStatus getAuctionStatus();

    String getAuctionRegisteredAt();

    PostPointActivateEnum getPostPointActive();
}
