package com.i5e2.likeawesomevegetable.payment.order.repository;

import com.i5e2.likeawesomevegetable.payment.order.AdminPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminPaymentOrderJpaRepository extends JpaRepository<AdminPaymentOrder, Long> {
    Optional<AdminPaymentOrder> findByAdminOrderId(String postOrderId);
}
