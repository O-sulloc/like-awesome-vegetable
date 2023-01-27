package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final CompanyBuyingJpaRepository buyingJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;

    public String creatBuying(BuyingRequest buyingRequest) {

        CompanyBuying companyBuying = buyingRequest.toEntity(buyingRequest);
        buyingJpaRepository.save(companyBuying);

        return null;
    }

    public String creatAuction(AuctionRequest auctionRequest) {

        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest);
//        farmAuctionJpaRepository.save(auctionRequest);

        return null;
    }


}
