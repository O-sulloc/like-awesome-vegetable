package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.user.MainItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainItemJpaRepository extends JpaRepository<MainItem, Long> {
}
