package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.user.User;
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

    @Query(nativeQuery = true, value = "select like_awesome_vegetable.t_standby.user_id\n" +
            "from like_awesome_vegetable.t_standby\n" +
            "where t_standby.farm_auction_id = ?1;")
    List<User> selectByFarmAuctionId(Long auctionId);
}
