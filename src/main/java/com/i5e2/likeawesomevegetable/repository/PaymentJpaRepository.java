package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.payment.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentInfo, Long> {
}
