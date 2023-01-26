package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final CompanyBuyingJpaRepository buyingJpaRepository;

    public String creatBuying(BuyingRequest buyingRequest) {

        CompanyBuying companyBuying = buyingRequest.toEntity(buyingRequest);
        buyingJpaRepository.save(companyBuying);

        return null;
    }

    public static String shippingConvert(int value){
        if (value==1){
            return "BOXING";
        } else if (value==2) {
            return "TONBAG";
        } else if (value==3) {
            return "CONTIBOX";
        }
        return "0";
    }
}
