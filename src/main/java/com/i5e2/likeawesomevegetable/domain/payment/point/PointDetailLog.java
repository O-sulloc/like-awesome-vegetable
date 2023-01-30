package com.i5e2.likeawesomevegetable.domain.payment.point;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "t_point_detail_log")
public class PointDetailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_detail_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_event_id")
    private PointEvent pointEvent;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_detail_status")
    private PointStatus pointStatus;

    @Column(name = "point_detail_history")
    private String pointDetailHistory;

    @Column(name = "point_target_user")
    private Long pointTargetUser;

    @Column(name = "point_detail_amount")
    private Long pointDetailAmount;

    @Column(name = "point_detail_event_at")
    private LocalDateTime pointDetailEventAt;

}
