package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmAuctionJpaRepository extends JpaRepository<FarmAuction, Long> {
}
