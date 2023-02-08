package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandByJpaRepository extends JpaRepository<Standby, Long> {
    Page<Standby> findAllByFarmAuctionId(Long id, Pageable pageable);

    Page<Standby> findByUser(User user, Pageable pageable);
}
