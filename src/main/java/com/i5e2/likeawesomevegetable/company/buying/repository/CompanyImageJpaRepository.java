package com.i5e2.likeawesomevegetable.company.buying.repository;

import com.i5e2.likeawesomevegetable.company.buying.CompanyImage;
import com.i5e2.likeawesomevegetable.user.company.dto.CompanyImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageJpaRepository extends JpaRepository<CompanyImage, Long> {

    List<CompanyImageLink> findByCompanyUserId(Long companyId);
}
