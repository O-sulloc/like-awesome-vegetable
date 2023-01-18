package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {
}
