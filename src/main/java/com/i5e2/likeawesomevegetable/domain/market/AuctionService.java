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

    public FarmAuction creatAuction(AuctionRequest auctionRequest, String email) {

        User user = userJpaRepository.findByEmail(email).get();
        FarmUser farmUser = user.getFarmUser();

//        FarmUser farmUser = farmUserRepository.findByFarmOwnerName(name).get();

        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest,farmUser);
        auctionJpaRepository.save(farmAuction);

        return farmAuction;
    }
}
