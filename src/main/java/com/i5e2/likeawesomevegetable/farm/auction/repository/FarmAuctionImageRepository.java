package com.i5e2.likeawesomevegetable.farm.auction.repository;

import com.i5e2.likeawesomevegetable.farm.auction.FarmAuctionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmAuctionImageRepository extends JpaRepository<FarmAuctionImage, Long> {

    List<FarmAuctionImage> findByFarmAuctionId(Long auctionId);

}
