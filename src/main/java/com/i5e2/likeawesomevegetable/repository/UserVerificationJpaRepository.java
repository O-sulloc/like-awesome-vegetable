package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.verification.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationJpaRepository extends JpaRepository<UserVerification, Long> {
}
