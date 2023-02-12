package com.i5e2.likeawesomevegetable.payment.point.repository;

import com.i5e2.likeawesomevegetable.payment.point.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findByUserId(Long userId);
}
