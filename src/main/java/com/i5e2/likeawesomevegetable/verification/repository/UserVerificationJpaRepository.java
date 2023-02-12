package com.i5e2.likeawesomevegetable.verification.repository;

import com.i5e2.likeawesomevegetable.verification.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationJpaRepository extends JpaRepository<UserVerification, Long> {
    Optional<UserVerification> findByUserId(Long loginUserId);
}
