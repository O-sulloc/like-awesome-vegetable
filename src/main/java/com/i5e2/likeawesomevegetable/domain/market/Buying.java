package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.common.Item;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_buying")
public class Buying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buying_id")
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @CreatedDate
    @Column(name = "start_time",updatable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "price")
    private Long price;

    @Column(name = "shipping")
    private Boolean shipping;

    @Column(name = "description",length = 5000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}