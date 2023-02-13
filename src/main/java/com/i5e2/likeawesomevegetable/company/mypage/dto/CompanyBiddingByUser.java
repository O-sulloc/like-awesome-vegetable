package com.i5e2.likeawesomevegetable.company.mypage.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.ParticipationStatus;
import com.i5e2.likeawesomevegetable.farm.bidding.dto.BiddingResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBiddingByUser {
    private Long biddingId;
    private Long companyUserId;
    private String managerName;
    private String auctionTitle;
    private String auctionStartTime;
    private String auctionEndTime;
    private String auctionItemCategory;
    private Integer auctionQuantity;
    private String auctionDescription;
    private String auctionRegisteredAt;
    private ParticipationStatus participationStatus;
    private Long auctionBiddingPrice;
    private Long biddingTime;
    private BiddingResult biddingResult;
    private String biddingNumber;
}
