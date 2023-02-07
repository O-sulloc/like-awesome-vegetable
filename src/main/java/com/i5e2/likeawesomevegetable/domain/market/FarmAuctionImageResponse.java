package com.i5e2.likeawesomevegetable.domain.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FarmAuctionImageResponse {
    private String fileName;
    private String message;

    public static FarmAuctionImageResponse of(String fileName,String message){
        return new FarmAuctionImageResponse(fileName,message);
    }
}
