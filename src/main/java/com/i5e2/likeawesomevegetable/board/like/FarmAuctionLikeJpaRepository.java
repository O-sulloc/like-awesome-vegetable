package com.i5e2.likeawesomevegetable.board.like;

import com.i5e2.likeawesomevegetable.farm.auction.FarmAuction;
import com.i5e2.likeawesomevegetable.user.basic.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmAuctionLikeJpaRepository extends JpaRepository<FarmAuctionLike, Long> {
    Optional<FarmAuctionLike> findByUserAndFarmAuction(User user, FarmAuction farmAuction);
}
