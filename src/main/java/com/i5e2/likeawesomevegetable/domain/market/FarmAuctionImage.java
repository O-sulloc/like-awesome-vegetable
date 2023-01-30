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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_auction_id")
    private FarmAuction farmAuction;

    //    @Column(name = "link")
//    private String link;
    @Column(name = "auction_image_link")
    private String auctionImageLink;
    
    // 추가
    @Column(name = "auction_image_name")
    private String auctionImageName;


}
