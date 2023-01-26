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
    @Column(name = "bidding_time")
    private Long biddingTime;


}
