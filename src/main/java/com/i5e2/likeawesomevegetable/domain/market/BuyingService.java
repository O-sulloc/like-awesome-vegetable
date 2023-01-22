package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.BuyingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final BuyingJpaRepository buyingJpaRepository;

    public String creatBuying(BuyingRequest buyingRequest) {

        Buying buying = buyingRequest.toEntity(buyingRequest);
        buyingJpaRepository.save(buying);

        return null;
    }
}
