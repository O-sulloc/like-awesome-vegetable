package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingLike;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBuyingLikeJpaRepository extends JpaRepository<CompanyBuyingLike, Long> {
    Optional<CompanyBuyingLike> findByUserAndCompanyBuying(User user, CompanyBuying companyBuying);
}
