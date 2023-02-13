package com.i5e2.likeawesomevegetable.company.mypage.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;

public interface CompanyBuyingByUser {
    Long getCompanyBuyingId();

    String getBuyingTitle();

    String getBuyingItemCode();

    String getCompanyAddress();

    Integer getBuyingPrice();

    String getBuyingRegisteredAt();

    PostPointActivateEnum getPostPointActivate();
}
