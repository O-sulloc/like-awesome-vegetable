package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserJpaRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByAdminEmail(String adminEmail);
}
