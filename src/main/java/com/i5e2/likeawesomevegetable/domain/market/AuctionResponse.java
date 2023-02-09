package com.i5e2.likeawesomevegetable.domain.market;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuctionResponse {

    private String message;
    private Long auctionId;

    public AuctionResponse(String message, Long auctionId) {
        this.message = message;
        this.auctionId = auctionId;
    }

    public AuctionResponse() {
    }
}
