package com.i5e2.likeawesomevegetable.payment.point.repository;

import com.i5e2.likeawesomevegetable.payment.point.PointEventLog;
import com.i5e2.likeawesomevegetable.payment.point.dto.PointTotalBalanceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointEventLogJpaRepository extends JpaRepository<PointEventLog, Long> {
    @Query(value = "select COALESCE(sum(log.pointEventAmount), 0) as userTotalBalance" +
            ", log.pointUserId as userId " +
            "from PointEventLog as log " +
            "where log.pointUserId = :id")
    PointTotalBalanceDto getUserTotalBalance(@Param("id") Long id);

    @Query(value = "select * " +
            "from t_point_event_log " +
            "where t_point_event_log.point_event_status not like 'ADMIN%' " +
            "and t_point_event_log.point_user_id = ? " +
            "order by t_point_event_log.point_used_event_at desc", nativeQuery = true)
    List<PointEventLog> getPointUserId(@Param("id") Long id);

    @Query(value = "select * " +
            "from t_point_event_log " +
            "where t_point_event_log.point_event_status like 'ADMIN%' " +
            "and t_point_event_log.point_user_id = ? " +
            "order by t_point_event_log.point_used_event_at desc", nativeQuery = true)
    List<PointEventLog> getPointAdminId(@Param("id") Long id);
}
