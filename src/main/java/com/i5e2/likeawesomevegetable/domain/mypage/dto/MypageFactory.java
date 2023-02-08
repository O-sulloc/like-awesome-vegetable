package com.i5e2.likeawesomevegetable.domain.mypage.dto;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.market.Standby;

public class MypageFactory {
    public static FarmApplyByUser from(Apply apply) {
        return FarmApplyByUser.builder()
                .applyId(apply.getId())
                .farmUserId(apply.getUser().getFarmUser().getId())
                .managerName(apply.getUser().getManagerName())
                .buyingTitle(apply.getBuyingTitle())
                .buyingStartTime(apply.getCompanyBuying().getBuyingStartTime())
                .buyingEndTime(apply.getCompanyBuying().getBuyingEndTime())
                .buyingItemCategory(apply.getCompanyBuying().getBuyingItemCategory())
                .buyingQuantity(apply.getCompanyBuying().getBuyingQuantity())
                .applyQuantity(apply.getApplyQuantity())
                .buyingDescription(apply.getCompanyBuying().getBuyingDescription())
                .buyingRegisteredAt(apply.getCompanyBuying().getBuyingRegisteredAt())
                .companyBuyingStatus(apply.getParticipationStatus())
                .applyTime(apply.getApplyTime())
                .applyNumber(apply.getApplyNumber())
                .build();
    }

    public static CompanyBiddingByUser from(Standby standby) {
        return CompanyBiddingByUser.builder()
                .biddingId(standby.getId())
                .companyUserId(standby.getUser().getCompanyUser().getId())
                .managerName(standby.getUser().getManagerName())
                .auctionTitle(standby.getAuctionTitle())
                .auctionStartTime(standby.getFarmAuction().getAuctionStartTime())
                .auctionEndTime(standby.getFarmAuction().getAuctionEndTime())
                .auctionItemCategory(standby.getFarmAuction().getAuctionItemCategory())
                .auctionQuantity(standby.getFarmAuction().getAuctionQuantity())
                .auctionBiddingPrice(standby.getBiddingPrice())
                .auctionDescription(standby.getFarmAuction().getAuctionDescription())
                .auctionRegisteredAt(standby.getFarmAuction().getAuctionRegisteredAt())
                .participationStatus(standby.getParticipationStatus())
                .biddingTime(standby.getBiddingTime())
                .biddingResult(standby.getBiddingResult())
                .biddingNumber(standby.getBiddingNumber())
                .build();
    }

}
