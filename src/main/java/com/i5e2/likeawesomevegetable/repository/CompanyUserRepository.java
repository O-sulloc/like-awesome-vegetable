package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
}
