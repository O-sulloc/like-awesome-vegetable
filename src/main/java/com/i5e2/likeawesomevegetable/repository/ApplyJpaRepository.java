package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
    @Query(value = "SELECT SUM(applyQuantity) FROM Apply WHERE Apply.companyBuying.id = id", nativeQuery = true)
    Long currentQuantity(@Param("id") Long id);

    Page<Apply> findAllByCompanyBuyingId(Long id, Pageable pageable);

    Page<Apply> findByUser(Pageable pageable, User user);
}
