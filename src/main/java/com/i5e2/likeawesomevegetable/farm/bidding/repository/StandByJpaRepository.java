package com.i5e2.likeawesomevegetable.farm.bidding.repository;

import com.i5e2.likeawesomevegetable.farm.bidding.Standby;
import com.i5e2.likeawesomevegetable.user.basic.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StandByJpaRepository extends JpaRepository<Standby, Long> {
    Page<Standby> findAllByFarmAuctionId(Long id, Pageable pageable);

    Page<Standby> findByUser(User user, Pageable pageable);

    boolean existsByFarmAuctionId(Long auctionId);

    @Query(value = "select * from t_standby where bidding_price = (select max(bidding_price) from t_standby where farm_auction_id = :auctionId)", nativeQuery = true)
    Standby findByFarmAuctionId(@Param("auctionId") Long auctionId);

    List<Standby> findAllByFarmAuctionId(Long auctionId);

    @Query(value = "select standBy.farmAuction.id " +
            "from Standby as standBy " +
            "where standBy.farmAuction.id = :farmAuctionId ")
    List<User> selectByFarmAuctionId(@Param("farmAuctionId") Long farmAuctionId);
}
