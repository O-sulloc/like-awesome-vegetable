package com.i5e2.likeawesomevegetable.domain.market;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BuyingResponse {

    private String message;
    private Long buyingId;

    public BuyingResponse(String message, Long buyingId) {
        this.message = message;
        this.buyingId = buyingId;
    }

    public BuyingResponse() {
    }
}
