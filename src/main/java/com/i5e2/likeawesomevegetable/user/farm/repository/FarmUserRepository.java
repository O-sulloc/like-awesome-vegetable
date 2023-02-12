package com.i5e2.likeawesomevegetable.user.farm.repository;

import com.i5e2.likeawesomevegetable.map.dto.FarmAddress;
import com.i5e2.likeawesomevegetable.user.farm.FarmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FarmUserRepository extends JpaRepository<FarmUser, Long> {

    List<FarmAddress> findAllBy();
}
