package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmMypageJapRepository extends JpaRepository<FarmAuction, Long> {

    @Query(value = "select farm.id as farmAuctionId" +
            ", farm.auctionTitle as auctionTitle" +
            ", farm.auctionItem as auctionItemName" +
            ", farm.farmUser.farmAddress as farmAddress" +
            ", farm.auctionStartPrice as auctionStartPrice" +
            ", farm.auctionRegisteredAt as auctionRegisteredAt" +
            ", farm.postPointActivate as postPointActivate " +
            "from FarmAuction farm " +
            "where farm.farmUser.id = :farmUserId")
    List<FarmAuctionByUser> findByFarmAuctions(Long farmUserId, Pageable pageable);
}
