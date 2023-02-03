package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, String> {
}
