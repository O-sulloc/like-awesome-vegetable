package com.i5e2.likeawesomevegetable.company.apply.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.ParticipationStatus;
import com.i5e2.likeawesomevegetable.company.apply.Apply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyResponse {

    private Long applyQuantity;
    private Long companyBuyingId;
    private Long userId;
    private ParticipationStatus participationStatus;
    private String buyingTitle;
    private String applyNumber;

    public static ApplyResponse fromEntity(Apply savedApply) {
        return ApplyResponse.builder()
                .applyQuantity(savedApply.getApplyQuantity())
                .participationStatus(savedApply.getParticipationStatus())
                .buyingTitle(savedApply.getBuyingTitle())
                .applyNumber(savedApply.getApplyNumber())
                .companyBuyingId(savedApply.getCompanyBuying().getId())
                .userId(savedApply.getUser().getId())
                .build();
    }
}