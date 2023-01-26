package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_farm_auction_image")
public class FarmAuctionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_auction_image_id")
    private Long id;
    @Column(name = "link")
    private String like;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_auction_id")
    private FarmAuction farmAuction;

}
