package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StandByJpaRepository extends JpaRepository<Standby, Long> {
    Page<Standby> findAllByFarmAuctionId(Long id, Pageable pageable);

    Page<Standby> findByUser(User user, Pageable pageable);

    boolean existsByFarmAuctionId(Long auctionId);

    @Query(value = "select * from t_standby where bidding_price = (select max(bidding_price) from t_standby where farm_auction_id = :auctionId)", nativeQuery = true)
    Standby findByFarmAuctionId(@Param("auctionId") Long auctionId);
}
