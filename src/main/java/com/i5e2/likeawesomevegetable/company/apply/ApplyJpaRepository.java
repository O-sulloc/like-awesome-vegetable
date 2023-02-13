package com.i5e2.likeawesomevegetable.company.apply;

import com.i5e2.likeawesomevegetable.user.basic.User;
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

    @Query(value = "select apply.user " +
            "from Apply as apply " +
            "where apply.companyBuying.id =: buyingId")
    List<User> selectByCompanyBuyingId(Long buyingId);
}
