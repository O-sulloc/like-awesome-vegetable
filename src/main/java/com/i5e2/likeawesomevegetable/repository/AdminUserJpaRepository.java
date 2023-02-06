package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserJpaRepository extends JpaRepository<AdminUser, Long> {
}
