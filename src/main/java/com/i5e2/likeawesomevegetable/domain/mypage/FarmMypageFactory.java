package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;

public class FarmMypageFactory {
    
    public static FarmApplyByUser from(Apply apply) {
        return FarmApplyByUser.builder()
                .applyId(apply.getId())
                .farmUserId(apply.getUser().getFarmUser().getId())
                .managerName(apply.getUser().getManagerName())
                .buyingTitle(apply.getBuyingTitle())
                .buyingStartTime(apply.getCompanyBuying().getBuyingStartTime())
                .buyingEndTime(apply.getCompanyBuying().getBuyingEndTime())
                .buyingItemCategory(apply.getCompanyBuying().getBuyingItemCategory())
                .buyingQuantity(apply.getCompanyBuying().getBuyingQuantity())
                .applyQuantity(apply.getApplyQuantity())
                .buyingDescription(apply.getCompanyBuying().getBuyingDescription())
                .buyingRegisteredAt(apply.getCompanyBuying().getBuyingRegisteredAt())
                .companyBuyingStatus(apply.getCompanyBuyingStatus())
                .applyTime(apply.getApplyTime())
                .applyNumber(apply.getApplyNumber())
                .build();
    }
}
