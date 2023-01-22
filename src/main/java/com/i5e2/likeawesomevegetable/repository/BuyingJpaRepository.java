package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.Buying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingJpaRepository extends JpaRepository<Buying, Long> {
}
