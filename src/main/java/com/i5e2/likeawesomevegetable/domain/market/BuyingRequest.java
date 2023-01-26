package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyingRequest {
    //TODO : 1. 후입력(endtime,userid) 2.이미지 업로드 3.리턴타입

//    - 기업명 userName
//- 품목 item
//- 수량 quantity
//- 기업 설명 description
//- 모집 기간 startTime
//- 금액 price
//- 운임 여부 shipping
//- 카테고리 ??
//- 검증마크 ??

    private String userName;
    private String item;
    @Min(value = 0)
    private Long quantity;
    @NotNull
    private String description;
    private LocalDateTime startTime; // String?
    @Min(value = 0)
    private Long price;
    private Boolean shipping;

    public CompanyBuying toEntity(BuyingRequest buyingRequest) {
        return CompanyBuying.builder()
//                .user(buyingRequest.user.get)
//                .item(buyingRequest.item.get)
                .description(buyingRequest.getDescription())
                .quantity(buyingRequest.getQuantity())
                .startTime(LocalDateTime.now()) //디테일 수정
//                .endTime(buyingRequest.set)
                .price(buyingRequest.price)
//                .shipping(buyingRequest.shipping)
                .build();
    }


}
