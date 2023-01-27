package com.i5e2.likeawesomevegetable.domain.apply.dto;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequest {

    private Long supplyQuantity;

    public Apply toEntity(Long supplyQuantity, CompanyBuying companyBuying, User user) {
        return Apply.builder()
                .supplyQuantity(supplyQuantity)
                .companyBuying(companyBuying)
                .user(user)
                .build();
    }
}