package com.i5e2.likeawesomevegetable.farm.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgFindListResponse {

    private Long id;
    private String auctionImageLink;
    private String auctionImageName;
}
