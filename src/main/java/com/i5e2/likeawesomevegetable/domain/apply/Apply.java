package com.i5e2.likeawesomevegetable.domain.apply;

import com.i5e2.likeawesomevegetable.domain.market.Buying;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @Column(name = "supply_quantity")
    private Long supplyQuantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "buying_id")
    private Buying buying;
}