package com.i5e2.likeawesomevegetable.domain.auction;

import com.i5e2.likeawesomevegetable.domain.common.Item;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;
import org.attoparser.dom.Text;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_auction")
@EntityListeners(AuditingEntityListener.class)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    @Column(name = "description", nullable = false)
    private Text description;
    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private String registeredAt;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "start_price", nullable = false)
    private Long startPrice;
    @Column(name = "end_price")
    private Long endPrice;
    @Column(name = "limit_price")
    private Long limitPrice;
    @Column(name = "shipping", nullable = false)
    private Boolean shipping;
    @Column(name = "status", nullable = false)
    private Long status; //enum
    @LastModifiedDate
    @Column(name = "modified_at")
    private String modifiedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "winner_price")
    private Long winnerPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @PrePersist
//      String으로 바꾸기
    public void onPrePersist() {
        this.registeredAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedAt = this.registeredAt;
    }

    @PreUpdate
    public void onPreUpdatePersist() {
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
