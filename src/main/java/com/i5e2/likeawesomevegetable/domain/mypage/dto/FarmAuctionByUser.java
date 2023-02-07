package com.i5e2.likeawesomevegetable.domain.mypage.dto;

import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;

public interface FarmAuctionByUser {

    Long getFarmAuctionId();

    String getAuctionTitle();

    String getAuctionItemName();

    String getFarmAddress();

    Integer getAuctionStartPrice();

    String getAuctionRegisteredAt();

    PostPointActivateEnum getPostPointActivate();
}
