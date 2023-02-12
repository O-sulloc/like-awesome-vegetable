package com.i5e2.likeawesomevegetable.payment.order.repository;

import com.i5e2.likeawesomevegetable.payment.order.UserPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPaymentOrderJpaRepository extends JpaRepository<UserPaymentOrder, Long> {
    Optional<UserPaymentOrder> findByPostOrderId(String postOrderId);
}
