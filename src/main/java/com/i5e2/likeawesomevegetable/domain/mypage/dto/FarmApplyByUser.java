package com.i5e2.likeawesomevegetable.domain.mypage.dto;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmApplyByUser {
    private Long applyId;
    private Long farmUserId;
    private String managerName;
    private String buyingTitle;
    private String buyingStartTime;
    private String buyingEndTime;
    private Integer buyingItemCategory;
    private Integer buyingQuantity;
    private String buyingDescription;
    private String buyingRegisteredAt;
    private CompanyBuyingStatus companyBuyingStatus;
    private Long applyQuantity;
    private LocalDateTime applyTime;
    private String applyNumber;
}
