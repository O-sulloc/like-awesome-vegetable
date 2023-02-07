package com.i5e2.likeawesomevegetable.domain.apply.dto;

import com.i5e2.likeawesomevegetable.domain.apply.BiddingResult;
import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiddingResponse {

    private Long biddingPrice;
    private Long farmAuctionId;
    private Long userId;
    private FarmAuctionStatus farmAuctionStatus;
    private BiddingResult biddingResult;
    private String auctionTitle;
    private String biddingNumber;

    public static BiddingResponse fromEntity(Standby savedBidding) {
        return BiddingResponse.builder()
                .biddingPrice(savedBidding.getBiddingPrice())
                .farmAuctionId(savedBidding.getFarmAuction().getId())
                .userId(savedBidding.getUser().getId())
                .farmAuctionStatus(savedBidding.getFarmAuctionStatus())
                .biddingResult(savedBidding.getBiddingResult())
                .auctionTitle(savedBidding.getAuctionTitle())
                .biddingNumber(savedBidding.getBiddingNumber())
                .build();
    }
}
