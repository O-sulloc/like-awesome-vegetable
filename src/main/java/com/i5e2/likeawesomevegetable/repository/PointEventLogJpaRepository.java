package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.point.dto.PointTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointEventLogJpaRepository extends JpaRepository<PointEventLog, Long> {
    @Query(value = "select sum(log.pointEventAmount) as userTotalBalance" +
            ", log.pointUserId as userId " +
            "from PointEventLog as log " +
            "where log.pointUserId = :id")
    PointTotalBalanceDto getUserTotalBalance(@Param("id") Long id);
}
