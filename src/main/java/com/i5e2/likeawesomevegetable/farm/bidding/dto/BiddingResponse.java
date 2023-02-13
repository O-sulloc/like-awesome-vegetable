package com.i5e2.likeawesomevegetable.farm.bidding.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.ParticipationStatus;
import com.i5e2.likeawesomevegetable.farm.bidding.Standby;
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
    private ParticipationStatus participationStatus;
    private BiddingResult biddingResult;
    private String auctionTitle;
    private String biddingNumber;

    public static BiddingResponse fromEntity(Standby savedBidding) {
        return BiddingResponse.builder()
                .biddingPrice(savedBidding.getBiddingPrice())
                .farmAuctionId(savedBidding.getFarmAuction().getId())
                .userId(savedBidding.getUser().getId())
                .participationStatus(savedBidding.getParticipationStatus())
                .biddingResult(savedBidding.getBiddingResult())
                .auctionTitle(savedBidding.getAuctionTitle())
                .biddingNumber(savedBidding.getBiddingNumber())
                .build();
    }
}
