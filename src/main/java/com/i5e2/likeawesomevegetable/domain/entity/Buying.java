package com.i5e2.likeawesomevegetable.domain.entity;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_buying")
public class Buying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buying_id")
    private Long id;

    @Column(name = "recruitment_quantity")
    private Long recruitmentQuantity;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "price")
    private Long price;

    @Column(name = "shipping")
    private Boolean shipping;

    @OneToMany(mappedBy = "buying", fetch = FetchType.LAZY)
    private List<Apply> apply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "item_id")
    private Item item;
}