package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionService {
    private final FarmAuctionJpaRepository auctionJpaRepository;

    public FarmAuction creatAuction(AuctionRequest auctionRequest) {

        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest);
        auctionJpaRepository.save(farmAuction);

        return farmAuction;
    }
}
