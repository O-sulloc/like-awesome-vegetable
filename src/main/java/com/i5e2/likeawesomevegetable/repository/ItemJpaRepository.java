package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.common.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
