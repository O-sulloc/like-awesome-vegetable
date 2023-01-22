package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.AuctionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionService {
    private final AuctionJpaRepository auctionJpaRepository;

    public String creatAuction(AuctionRequest auctionRequest) {

        Auction auction = auctionRequest.toEntity(auctionRequest);

//        request에서 받은 부분말고 따로 추가
//        auction=Auction.builder()
//                .endPrice(123L)
//                .endTime(LocalDateTime.now())
//                .build();
        auctionJpaRepository.save(auction);

        return null;
    }
}
