package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
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

    //    @Column(name = "title")
    //    private String title;
    @Column(name = "auction_title")
    private String auctionTitle;

    //    @Column(name = "category")
//    private String category;
    @Column(name = "auction_item_category")
    private String auctionItemCategory;

    //    @Column(name = "item")
//    private String item;
    @Column(name = "auction_item")
    private String auctionItem;

    //    @Column(name = "quantity")
//    private Long quantity;
    @Column(name = "auction_quantity")
    private Long auctionQuantity;

    //    @Column(name = "description", length = 5000)
//    private String description;
    @Column(name = "auction_description", length = 5000)
    private String auctionDescription;

    // 추가
    @Column(name = "auction_start_time")
    private LocalDateTime auctionStartTime;

    //    @Column(name = "end_time")
//    private String endTime;
    @Column(name = "auction_end_time")
    private LocalDateTime auctionEndTime;

    //    @Column(name = "start_price")
//    private Long startPrice;
    @Column(name = "auction_start_price")
    private Long auctionStartPrice;

    // 추가
    @Column(name = "auction_highest_price")
    private Long auctionHighestPrice;

    //    @Column(name = "limit_price")
//    private Long limitPrice;
    @Column(name = "auction_limit_price")
    private Long auctionLimitPrice;

    // ERD에 ENUM으로 정의되어있음. Enum 맞춰서 수정 필요
//    @Column(name = "shipping")
//    private String shipping;
    @Column(name = "auction_shipping")
    private String auctionShipping;

    //    @Column(name = "status")
//    @Enumerated(value = EnumType.STRING)
//    private StatusEnum status;
    @Column(name = "auction_status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum auctionStatus;

    //    @Column(name = "registered_at")
//    private String registeredAt;
    @Column(name = "auction_registered_at")
    private LocalDateTime auctionRegisteredAt;

    //    @LastModifiedDate
//    @Column(name = "modified_at")
//    private String modifiedAt;
    @LastModifiedDate
    @Column(name = "auction_modified_at")
    private LocalDateTime auctionModifiedAt;

    //    @Column(name = "deleted_at")
//    private LocalDateTime deletedAt;
    @Column(name = "auction_deleted_at")
    private LocalDateTime auctionDeletedAt;

    // 추가
    @Column(name = "auction_tag")
    private String auctionTag;

    @PreUpdate
    public void onPreUpdatePersist() {
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}