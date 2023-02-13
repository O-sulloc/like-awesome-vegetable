package com.i5e2.likeawesomevegetable.farm.bidding.dto;

import com.i5e2.likeawesomevegetable.farm.auction.FarmAuction;
import com.i5e2.likeawesomevegetable.farm.bidding.Standby;
import com.i5e2.likeawesomevegetable.user.basic.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiddingRequest {

    private Long biddingPrice;

    public Standby toEntity(Long biddingPrice, FarmAuction farmAuction, User user, BiddingResult biddingResult) {
        return Standby.builder()
                .farmAuction(farmAuction)
                .participationStatus(farmAuction.getParticipationStatus())
                .biddingResult(biddingResult)
                .biddingPrice(biddingPrice)
                .auctionTitle(farmAuction.getAuctionTitle())
                .user(user)
                .build();
    }
}
