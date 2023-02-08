package com.i5e2.likeawesomevegetable.domain.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyBuyingDetailResponse {
    private Long id;
    private String buyingTitle;
    private String buyingDescription;
    private String buyingStartTime;
    private String buyingEndTime;
    private String buyingItem;
    private String buyingItemCategory;
    private Integer buyingPrice;
    private Integer buyingQuantity;
    private String buyingShipping;
    private String receiverAddress;
    private String receiverName;
    private String receiverPhoneNo;
    private Long companyUserId;
    private ParticipationStatus companyBuyingStatus;
    private PostPointActivateEnum postPointActivate;
    private String buyingRegisteredAt;
    private String buyingModifiedAt;
    private LocalDateTime buyingDeletedAt;
}
