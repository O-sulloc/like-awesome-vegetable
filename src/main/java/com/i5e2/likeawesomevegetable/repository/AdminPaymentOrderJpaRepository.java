package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminPaymentOrderJpaRepository extends JpaRepository<AdminPaymentOrder, Long> {
    Optional<AdminPaymentOrder> findByAdminOrderId(String postOrderId);
}
