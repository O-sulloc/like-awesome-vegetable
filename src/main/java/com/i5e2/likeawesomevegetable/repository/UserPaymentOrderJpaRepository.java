package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPaymentOrderJpaRepository extends JpaRepository<UserPaymentOrder, Long> {
    Optional<UserPaymentOrder> findByPostOrderId(String postOrderId);
}
