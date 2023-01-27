package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "start_price")
    private Long startPrice;

    @Column(name = "end_price")
    private Long endPrice;

    @Column(name = "limit_price")
    private Long limitPrice;

    @Column(name = "shipping")
    @Enumerated(value = EnumType.STRING)
    private ShippingEnum shipping;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @LastModifiedDate
    @Column(name = "modified_at")
    private String modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "winner_price")
    private Long winnerPrice;

    @Column(name = "item")
    private String item;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @PreUpdate
    public void onPreUpdatePersist() {
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
