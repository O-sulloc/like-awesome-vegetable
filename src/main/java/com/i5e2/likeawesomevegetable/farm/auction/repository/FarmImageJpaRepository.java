package com.i5e2.likeawesomevegetable.farm.auction.repository;

import com.i5e2.likeawesomevegetable.farm.auction.dto.FarmImageLink;
import com.i5e2.likeawesomevegetable.farm.auction.service.FarmImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmImageJpaRepository extends JpaRepository<FarmImage, Long> {

    List<FarmImageLink> findAllByFarmUserId(Long farmId);
}
