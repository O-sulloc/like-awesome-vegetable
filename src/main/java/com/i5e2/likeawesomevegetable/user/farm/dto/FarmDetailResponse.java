package com.i5e2.likeawesomevegetable.user.farm.dto;

import com.i5e2.likeawesomevegetable.farm.auction.dto.FarmImageLink;
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
