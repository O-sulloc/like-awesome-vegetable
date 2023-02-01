package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.FarmFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmFileJpaRepository extends JpaRepository<FarmFile, Long> {
}
