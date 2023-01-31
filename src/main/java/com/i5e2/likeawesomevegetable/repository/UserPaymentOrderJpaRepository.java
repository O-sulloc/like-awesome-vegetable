package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.payment.api.UserPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentOrderJpaRepository extends JpaRepository<UserPaymentOrder, Long> {
}
