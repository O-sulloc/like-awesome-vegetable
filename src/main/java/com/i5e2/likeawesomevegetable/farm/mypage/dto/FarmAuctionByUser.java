package com.i5e2.likeawesomevegetable.farm.mypage.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;

public interface FarmAuctionByUser {
    Long getFarmAuctionId();

    String getAuctionTitle();

    String getAuctionItemName();

    String getFarmAddress();

    Integer getAuctionStartPrice();

    String getAuctionRegisteredAt();

    PostPointActivateEnum getPostPointActivate();

}
