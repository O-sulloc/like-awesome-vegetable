package com.i5e2.likeawesomevegetable.domain.point.entity;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "t_user_point")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "point_total_balance")
    private Long pointTotalBalance;

    @Column(name = "deposit_total_balance")
    private Long depositTotalBalance;

    public UserPoint(User user, Long pointTotalBalance, Long depositTotalBalance) {
        this.user = user;
        this.pointTotalBalance = pointTotalBalance;
        this.depositTotalBalance = depositTotalBalance;
    }

    public void updatePointTotalBalance(Long pointTotalBalance) {
        this.pointTotalBalance = pointTotalBalance;
    }

    public void updateDepositTotalBalance(Long depositTotalBalance) {
        this.depositTotalBalance = depositTotalBalance;
    }
}
