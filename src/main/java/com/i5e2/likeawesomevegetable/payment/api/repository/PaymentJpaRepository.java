package com.i5e2.likeawesomevegetable.payment.api.repository;

import com.i5e2.likeawesomevegetable.payment.api.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, String> {
}
