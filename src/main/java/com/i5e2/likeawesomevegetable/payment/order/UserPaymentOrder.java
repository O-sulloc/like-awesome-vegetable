package com.i5e2.likeawesomevegetable.payment.order;

import com.i5e2.likeawesomevegetable.user.basic.User;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "t_user_payment_order")
public class UserPaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_payment_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "post_order_id")
    private String postOrderId;

    @Column(name = "payment_order_post")
    private String paymentOrderPost;

    @Column(name = "payment_order_amount")
    private Long paymentOrderAmount;

    @Builder
    public UserPaymentOrder(User user, String postOrderId, String paymentOrderPost, Long paymentOrderAmount) {
        this.user = user;
        this.postOrderId = postOrderId;
        this.paymentOrderPost = paymentOrderPost;
        this.paymentOrderAmount = paymentOrderAmount;
    }
}
