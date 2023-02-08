package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyBuyingJpaRepository extends JpaRepository<CompanyBuying, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM `like_awesome_vegetable`.t_company_buying \n" +
            "where post_point_activate = \'Able\'")
    List<CompanyBuying> findAllByPostPointActivate(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM like_awesome_vegetable.t_company_buying t\n" +
            "LEFT OUTER JOIN like_awesome_vegetable.t_company_buying_like tt ON t.company_buying_id = tt.company_buying_id\n" +
            "WHERE post_point_activate = 'Able'\n" +
            "GROUP BY t.company_buying_id\n" +
            "ORDER BY count(tt.company_buying_id) DESC")
    List<CompanyBuying> findAllByPostPointActivatewithHot(Pageable pageable);
}
