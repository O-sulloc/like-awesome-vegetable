package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MypageCompanyBuyingJpaRepository extends JpaRepository<CompanyBuying, Long> {
    @Query(value = "select company.id as companyBuyingId" +
            ", company.buyingTitle as buyingTitle" +
            ", company.buyingItem as buyingItemCode" +
            ", company.companyUser.companyAddress as companyAddress" +
            ", company.buyingPrice as buyingPrice" +
            ", company.buyingRegisteredAt as buyingRegisteredAt" +
            ", company.postPointActivate as postPointActivate " +
            "from CompanyBuying company " +
            "where company.companyUser.id = :companyUserId")
    List<FarmAuctionByUser> findByCompanyBuyings(Long companyUserId, Pageable pageable);
}
