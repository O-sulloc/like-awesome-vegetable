package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentMethod;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentStatus;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "t_payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_order_id")
    private UserPaymentOrder userPaymentOrder;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment-type")
    private PaymentType paymentType;

    @Column(name = "payment_request_at")
    private LocalDateTime paymentRequestAt;

    @Column(name = "payment_approved_at")
    private LocalDateTime paymentApprovedAt;

    @Column(name = "payment_last_transaction_key")
    private String paymentLastTransactionKey;

    @Column(name = "payment_order_name")
    private String paymentOrderName;

    @Column(name = "payment_key")
    private String paymentKey;

}
