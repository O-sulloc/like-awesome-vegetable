package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate t_item", nativeQuery = true)
    void truncateItem();
}
