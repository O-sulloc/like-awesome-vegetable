package com.i5e2.likeawesomevegetable.domain.payment.point;

import com.i5e2.likeawesomevegetable.domain.payment.api.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_payment_to_point")
public class PaymentToPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_to_point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "point_charge_amount")
    private Long pointChargeAmount;

    @Column(name = "point_balance")
    private Long pointBalance;

    @Column(name = "point_charge_date_at")
    private LocalDateTime pointChargeDateAt;

    @Column(name = "user_id")
    private Long userId;

}
