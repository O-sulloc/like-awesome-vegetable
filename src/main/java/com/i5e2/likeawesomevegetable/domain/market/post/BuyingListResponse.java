package com.i5e2.likeawesomevegetable.domain.market.post;

import com.i5e2.likeawesomevegetable.domain.market.ParticipationStatus;
import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyingListResponse {
    private Long id;
    private String companyName;
    private String buyingTitle;
    private String buyingStartTime;
    private String buyingEndTime;
    private String buyingItemCategory;
    private String buyingItem;
    private Integer buyingQuantity;
    private Integer buyingPrice;
    private ParticipationStatus companyBuyingStatus;
    private PostPointActivateEnum postPointActivate;

}
