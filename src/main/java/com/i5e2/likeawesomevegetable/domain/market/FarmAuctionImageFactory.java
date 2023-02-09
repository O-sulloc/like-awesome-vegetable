package com.i5e2.likeawesomevegetable.domain.market;

public class FarmAuctionImageFactory {

    public static ImgFindListResponse from(FarmAuctionImage farmAuctionImage) {
        return ImgFindListResponse.builder()
                .id(farmAuctionImage.getId())
                .auctionImageLink(farmAuctionImage.getAuctionImageLink())
                .auctionImageName(farmAuctionImage.getAuctionImageName())
                .build();
    }
}
