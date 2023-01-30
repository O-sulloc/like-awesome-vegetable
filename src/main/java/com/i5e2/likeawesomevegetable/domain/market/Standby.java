package com.i5e2.likeawesomevegetable.domain.market;


import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    // 추가
    // String이 아닌 Enum이어야 함. Enum 맞춰서 수정 필요
    @Column(name = "auction_status")
    private String auctionStatus;

    @Column(name = "bidding_time")
    private Long biddingTime;

    // 추가
    // String이 아닌 Enum이어야 함. Enum 맞춰서 수정 필요
    @Column(name = "bidding_result")
    private String biddingResult;

    @Column(name = "auction_title", length = 20)
    private String auctionTitle;

    @Column(name = "bidding_number", length = 100)
    private String biddingNumber;

}
