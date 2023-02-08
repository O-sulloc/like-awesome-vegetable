package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_farm_auction_like")
public class FarmAuctionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_auction_like_id")
    private Long farmAuctionLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_auction_id")
    private FarmAuction farmAuction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "farm_auction_like_registered_at")
    private LocalDateTime farmAuctionLikeRegisteredAt;

    @Builder
    public FarmAuctionLike(FarmAuction farmAuction, User loginUser) {
        this.farmAuction = farmAuction;
        this.user = loginUser;
        this.farmAuctionLikeRegisteredAt = LocalDateTime.now();
    }

}
