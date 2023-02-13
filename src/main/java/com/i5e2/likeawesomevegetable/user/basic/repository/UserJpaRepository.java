package com.i5e2.likeawesomevegetable.user.basic.repository;

import com.i5e2.likeawesomevegetable.user.basic.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByCompanyUserId(Long companyUserId);

    Optional<User> findByFarmUserId(Long farmUserId);
}
