package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long> {
}
