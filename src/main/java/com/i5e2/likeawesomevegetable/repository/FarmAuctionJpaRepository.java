package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmAuctionJpaRepository extends JpaRepository<FarmAuction, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM like_awesome_vegetable.t_farm_auction \n" +
            "where post_point_activate = \'Able\'")
    List<FarmAuction> findAllByPostPointActivate(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM like_awesome_vegetable.t_farm_auction t\n" +
            "LEFT OUTER JOIN like_awesome_vegetable.t_farm_auction_like tt ON t.farm_auction_id = tt.farm_auction_id\n" +
            "WHERE post_point_activate = 'Able'\n" +
            "GROUP BY t.farm_auction_id\n" +
            "ORDER BY count(tt.farm_auction_id) DESC")
    List<FarmAuction> findAllByPostPointActivatewithHot(Pageable pageable);
}
