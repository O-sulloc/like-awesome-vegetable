package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.item.Item;
import com.i5e2.likeawesomevegetable.domain.item.ItemLowestPriceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate t_item", nativeQuery = true)
    void truncateItem();

    @Query(value = "select auction.farm_auction_id       as farmAuctionId,\n" +
            "       auction.auction_start_price   as auctionStartPrice,\n" +
            "       auction.farm_user_id          as farmUserId,\n" +
            "       auction.auction_title         as auctionTitle,\n" +
            "       auction.auction_item          as auctionItem,\n" +
            "       auction.auction_item_category as auctionItemCategory,\n" +
            "       auction.auction_quantity as auctionQuantity,\n" +
            "       auction.auction_description as auctionDescription,\n" +
            "       auction.auction_start_time as auctionStartTime,\n" +
            "       auction.auction_end_time as auctionEndTime,\n" +
            "       auction.auction_highest_price as auctionHighestPrice,\n" +
            "       auction.auction_limit_price as auctionLimitPrice,\n" +
            "       auction.auction_status as auctionStatus,\n" +
            "       auction.auction_registered_at as auctionRegisteredAt,\n" +
            "       auction.post_point_activate as postPointActive " +
            "from t_farm_auction as auction " +
            "where match(auction_item) AGAINST(? in boolean mode) " +
            "order by auction_start_price asc " +
            "limit 0, 5", nativeQuery = true)
    List<ItemLowestPriceResponse> getLowestPriceFive(@Param("auctionItem") String auctionItem);
}