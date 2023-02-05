package com.i5e2.likeawesomevegetable.domain.admin;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "t_admin_payment_order")
public class AdminPaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_payment_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminUser adminUser;

    @Column(name = "admin_order_id")
    private String adminOrderId;

    @Column(name = "admin_order_info")
    private String adminOrderInfo;

    @Column(name = "admin_transfer_amount")
    private Long adminTransferAmount;
}
