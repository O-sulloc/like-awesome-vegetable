package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.map.FarmAddress;
import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FarmUserRepository extends JpaRepository<FarmUser, Long> {

    List<FarmAddress> findAllBy();
    Optional<FarmUser> findByFarmOwnerName(String name);
}
