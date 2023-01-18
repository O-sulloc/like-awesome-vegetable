package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionJpaRepository extends JpaRepository<Auction, Long> {
}
