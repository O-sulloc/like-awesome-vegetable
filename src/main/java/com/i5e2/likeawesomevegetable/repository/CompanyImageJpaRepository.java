package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.CompanyImage;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.CompanyImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageJpaRepository extends JpaRepository<CompanyImage, Long> {

    List<CompanyImageLink> findByCompanyUserId(Long companyId);
}
