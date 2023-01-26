package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmUserRepository extends JpaRepository<FarmUser, Long> {
}
