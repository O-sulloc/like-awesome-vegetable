package com.i5e2.likeawesomevegetable.domain.payment.api.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "t_payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private String id;//paymentKey

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_order_id")
    private UserPaymentOrder userPaymentOrder;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_requested_at")
    private String paymentRequestedAt;

    @Column(name = "payment_approved_at")
    private String paymentApprovedAt;

    @Column(name = "payment_last_transaction_key")
    private String paymentLastTransactionKey;

    @Column(name = "payment_order_name")
    private String paymentOrderName;

    @Builder
    public Payment(String id, UserPaymentOrder userPaymentOrder, Long paymentAmount, String paymentMethod
            , String paymentStatus, String paymentType, String paymentRequestedAt
            , String paymentApprovedAt, String paymentLastTransactionKey, String paymentOrderName) {
        this.id = id;
        this.userPaymentOrder = userPaymentOrder;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.paymentRequestedAt = paymentRequestedAt;
        this.paymentApprovedAt = paymentApprovedAt;
        this.paymentLastTransactionKey = paymentLastTransactionKey;
        this.paymentOrderName = paymentOrderName;
    }
}
