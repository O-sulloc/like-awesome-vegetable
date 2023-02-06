package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.map.CompanyAddress;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyUserJpaRepository extends JpaRepository<CompanyUser, Long> {
    List<CompanyAddress> findAllBy();
}
