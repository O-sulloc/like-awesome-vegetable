package com.i5e2.likeawesomevegetable.company.buying.repository;

import com.i5e2.likeawesomevegetable.board.like.CompanyBuyingLike;
import com.i5e2.likeawesomevegetable.company.buying.CompanyBuying;
import com.i5e2.likeawesomevegetable.user.basic.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBuyingLikeJpaRepository extends JpaRepository<CompanyBuyingLike, Long> {
    Optional<CompanyBuyingLike> findByUserAndCompanyBuying(User user, CompanyBuying companyBuying);
}
