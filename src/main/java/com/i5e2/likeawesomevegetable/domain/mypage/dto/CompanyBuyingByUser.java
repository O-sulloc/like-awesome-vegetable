package com.i5e2.likeawesomevegetable.domain.mypage.dto;

import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;

public interface CompanyBuyingByUser {
    Long getCompanyBuyingId();

    String getBuyingTitle();

    String getBuyingItemCode();

    String getCompanyAddress();

    Integer getBuyingPrice();

    String getBuyingRegisteredAt();

    PostPointActivateEnum getPostPointActivate();
}
