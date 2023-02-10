package com.i5e2.likeawesomevegetable.domain.market;


import com.i5e2.likeawesomevegetable.domain.apply.BiddingResult;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_standby")
// table명 t_bidding으로 변경해야 함. 변경하면 브랜치 에러날 것 같아서 수정 안함.
public class Standby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "standby_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) //게시글 id
    @JoinColumn(name = "farm_auction_id")
    private FarmAuction farmAuction;

    @Column(name = "bidding_price")
    private Long biddingPrice;

    @Column(name = "auction_status")
    private ParticipationStatus participationStatus;

    @CreatedDate
    @Column(name = "bidding_time")
    private Long biddingTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "bidding_result")
    private BiddingResult biddingResult;

    @Column(name = "auction_title", length = 20)
    private String auctionTitle;

    @Column(name = "bidding_number", length = 100)
    private String biddingNumber;

    public void setBiddingNumber(String biddingNumber) {
        this.biddingNumber = biddingNumber;
    }

    public void updateBiddingResult(BiddingResult biddingResult) {
        this.biddingResult = biddingResult;
    }

    public void updateStatusToEnd() {
        this.participationStatus = ParticipationStatus.END;
    }
}
