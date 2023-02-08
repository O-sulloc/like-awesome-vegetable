package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuctionLike;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmAuctionLikeJpaRepository extends JpaRepository<FarmAuctionLike, Long> {
    Optional<FarmAuctionLike> findByUserAndFarmAuction(User user, FarmAuction farmAuction);
}
