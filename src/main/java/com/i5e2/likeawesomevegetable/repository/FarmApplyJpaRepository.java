package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmApplyJpaRepository extends JpaRepository<Apply, Long> {
    Page<Apply> findByUser(Pageable pageable, User user);
}
