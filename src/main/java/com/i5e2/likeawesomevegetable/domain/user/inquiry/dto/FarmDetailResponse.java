package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import com.i5e2.likeawesomevegetable.domain.user.inquiry.FarmImageLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmDetailResponse {

    private List<FarmImageLink> farmImage;
    private FarmUserResponse farmUserResponse;
    private Page<AuctionListResponse> auctionListResponses;
}
