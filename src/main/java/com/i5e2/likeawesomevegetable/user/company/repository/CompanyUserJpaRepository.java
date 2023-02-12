package com.i5e2.likeawesomevegetable.user.company.repository;

import com.i5e2.likeawesomevegetable.map.dto.CompanyAddress;
import com.i5e2.likeawesomevegetable.user.company.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyUserJpaRepository extends JpaRepository<CompanyUser, Long> {
    List<CompanyAddress> findAllBy();
}
