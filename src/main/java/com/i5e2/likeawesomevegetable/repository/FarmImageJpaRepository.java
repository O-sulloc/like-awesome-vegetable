package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.FarmImage;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.FarmImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmImageJpaRepository extends JpaRepository<FarmImage, Long> {

    List<FarmImageLink> findAllByFarmUserId(Long farmId);
}
