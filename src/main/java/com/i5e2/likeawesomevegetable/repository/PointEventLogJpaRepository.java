package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointEventLogJpaRepository extends JpaRepository<PointEventLog, Long> {
}
