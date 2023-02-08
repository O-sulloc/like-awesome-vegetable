package com.i5e2.likeawesomevegetable.domain.market.like.dto;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingLike;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuctionLike;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {
    private Long postId;
    private String message;

    public static LikeResponse of(FarmAuctionLike farmAuctionLike, String message) {
        return new LikeResponse(farmAuctionLike.getFarmAuction().getId(), message);
    }

    public static LikeResponse of(CompanyBuyingLike companyBuyingLike, String message) {
        return new LikeResponse(companyBuyingLike.getCompanyBuying().getId(), message);
    }

}
