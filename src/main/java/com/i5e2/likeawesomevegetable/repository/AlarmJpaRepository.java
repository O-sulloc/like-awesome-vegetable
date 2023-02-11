package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {

    @Query(nativeQuery = true, value = "select *\n" +
            "from like_awesome_vegetable.t_alarm\n" +
            "where user_id = ?1 and alarm_read = false")
    List<Alarm> findAllByUserId(Long userId);
}
