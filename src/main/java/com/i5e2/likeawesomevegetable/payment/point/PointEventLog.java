package com.i5e2.likeawesomevegetable.payment.point;

import com.i5e2.likeawesomevegetable.payment.api.Payment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "t_point_event_log")
public class PointEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_event_log_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "point_event_status")
    private String pointEventStatus;

    @Column(name = "point_event_history")
    private String pointEventHistory;

    @Column(name = "point_target_user")
    private Long pointTargetUserId;

    @Column(name = "point_event_amount")
    private Long pointEventAmount;

    @Column(name = "point_request_at")
    private String pointRequestAt;

    @Column(name = "point_approved_at")
    private String pointApprovedAt;

    @Column(name = "point_user_id")
    private Long pointUserId;

    @Column(name = "point_used_event_at")
    private LocalDateTime pointUsedEventAt;

    @Builder
    public PointEventLog(Payment payment, String pointEventStatus, String pointEventHistory
            , Long pointEventAmount, String pointRequestAt, String pointApprovedAt, Long pointUserId) {
        this.payment = payment;
        this.pointEventStatus = pointEventStatus;
        this.pointEventHistory = pointEventHistory;
        this.pointEventAmount = pointEventAmount;
        this.pointRequestAt = pointRequestAt;
        this.pointApprovedAt = pointApprovedAt;
        this.pointUserId = pointUserId;
        this.pointUsedEventAt = LocalDateTime.now();
    }
}
