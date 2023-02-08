package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionService {
    private final FarmAuctionJpaRepository auctionJpaRepository;
    private final FarmUserRepository farmUserRepository;
    private final UserJpaRepository userJpaRepository;



    public FarmAuction creatAuctionNoneAuth(AuctionRequest auctionRequest) {

        FarmAuction farmAuction = auctionRequest.toEntityNone(auctionRequest);
        auctionJpaRepository.save(farmAuction);

        return farmAuction;
    }
    public FarmAuction creatAuction(AuctionRequest auctionRequest, String email) {

        User user = userJpaRepository.findByEmail(email).get();
        FarmUser farmUser = user.getFarmUser();

        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest,farmUser);
        auctionJpaRepository.save(farmAuction);

        return farmAuction;
    }

    public AuctionResponse createAuctionResponse(AuctionRequest auctionRequest,String email){
        User user = userJpaRepository.findByEmail(email).get();
        FarmUser farmUser = user.getFarmUser();

        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest,farmUser);
        auctionJpaRepository.save(farmAuction);

        AuctionResponse auctionResponse = AuctionResponse.builder()
                .auctionId(farmAuction.getId())
                .message("경매 게시글 작성 완료")
                .build();
        return auctionResponse;

    }
}
