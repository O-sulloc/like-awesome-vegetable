package com.i5e2.likeawesomevegetable.item.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.ParticipationStatus;
import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;

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
