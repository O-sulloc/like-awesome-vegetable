package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_farm_auction")
@EntityListeners(AuditingEntityListener.class)
public class FarmAuction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_auction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_user_id")
    private FarmUser farmUser;

    @Column(name = "auction_title")
    private String auctionTitle;

    @Column(name = "auction_start_time")
    private String auctionStartTime;

    @Column(name = "auction_end_time")
    private String auctionEndTime;

    @Column(name = "auction_item_category")
    private String auctionItemCategory;

    @Column(name = "auction_item")
    private String auctionItem;

    @Column(name = "auction_quantity")
    private Integer auctionQuantity;

    @Column(name = "auction_start_price")
    private Integer auctionStartPrice;

    // 추가 종료가격 입력
    @Column(name = "auction_highest_price")
    private Long auctionHighestPrice;

    @Column(name = "auction_limit_price")
    private Integer auctionLimitPrice;

    @Column(name = "auction_description", length = 5000)
    private String auctionDescription;

    @Column(name = "auction_shipping")
    private String auctionShipping;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "auction_status")
    private ParticipationStatus participationStatus;

    @CreatedDate
    @Column(name = "auction_registered_at", updatable = false)
    private String auctionRegisteredAt;

    @LastModifiedDate
    @Column(name = "auction_modified_at")
    private String auctionModifiedAt;

    @Column(name = "auction_deleted_at")
    private String auctionDeletedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "post_point_activate")
    private PostPointActivateEnum postPointActivate;

    @PrePersist
    public void onPrePersist() {
        this.auctionRegisteredAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.auctionModifiedAt = this.auctionRegisteredAt;
    }

    @PreUpdate
    public void onPreUpdatePersist() {
        this.auctionModifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void updateHighestPrice(Long auctionHighestPrice) {
        this.auctionHighestPrice = auctionHighestPrice;
    }
}