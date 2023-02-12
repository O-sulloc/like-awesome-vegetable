package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
    @Query(value = "SELECT sum(a.applyQuantity) FROM Apply a WHERE a.companyBuying.id = :id")
    Long currentQuantity(Long id);

    List<Apply> findAllByCompanyBuyingId(Long id);

    Page<Apply> findAllByCompanyBuyingId(Long id, Pageable pageable);

    Page<Apply> findByUser(Pageable pageable, User user);

    @Query(nativeQuery = true, value = "select like_awesome_vegetable.t_apply.user_id\n" +
            "from like_awesome_vegetable.t_apply\n" +
            "where like_awesome_vegetable.t_apply.company_buying_id = ?1")
    List<UserId> selectByCompanyBuyingId(Long buyingId);
}
