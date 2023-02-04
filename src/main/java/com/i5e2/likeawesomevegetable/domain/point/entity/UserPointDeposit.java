package com.i5e2.likeawesomevegetable.domain.point.entity;

import com.i5e2.likeawesomevegetable.domain.point.dto.DepositStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "t_user_point_deposit")
public class UserPointDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_deposit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    @Column(name = "deposit_amount")
    private Long depositAmount;

    @Column(name = "deposit_target_post_id")
    private Long depositTargetPostId;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_status")
    private DepositStatus depositStatus;

    @Column(name = "deposit_commission")
    private Long depositCommission;

    @Column(name = "deposit_type")
    private String depositType;

    @Column(name = "deposit_pending_at")
    private LocalDateTime depositPendingAt;

    @Column(name = "deposit_transfer_at")
    private LocalDateTime depositTransferAt;

    @Builder(builderMethodName = "setPendingDeposit")
    public UserPointDeposit(UserPoint userPoint, Long depositAmount, Long depositTargetPostId, DepositStatus depositStatus
            , Long depositCommission, String depositType, LocalDateTime depositPendingAt, LocalDateTime depositTransferAt) {
        this.userPoint = userPoint;
        this.depositAmount = depositAmount;
        this.depositTargetPostId = depositTargetPostId;
        this.depositStatus = DepositStatus.PENDING;
        this.depositCommission = depositCommission;
        this.depositType = depositType;
        this.depositPendingAt = LocalDateTime.now();
    }
}
