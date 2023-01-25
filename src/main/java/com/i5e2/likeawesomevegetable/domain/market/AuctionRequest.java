package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRequest {
    //TODO : 1. 후입력(price,time,userid,time) 2.이미지 업로드 3.리턴타입
//- 농가 이름 user.getUser_name
//- 품목 (api 선택)
//- 수량 quantity
//- 농산물 설명(이미지 포함) description
//- 경매 시간 registeredAt
//- 최저(농가)/최고(푸드리퍼브) 금액  startPrice / endprice 후 입력
//- 운임 여부 shipping
//- 위치 주소 address


    //    @NotBlank
    private String userName;
    //    @NotBlank
    private String item;
    @NotNull
    @Min(value = 1)
    private Long quantity;
    @NotNull
    private String description;
    //    @FutureOrPresent //Localdatetime아니라 에러?
    private String registeredAt;
    //    @NotBlank
    @Min(value = 0)
//    @Max(value = endPrice) //??
    private Long startPrice;
    private Long endPrice;
    //    @NotBlank
    private Boolean shipping;
    @NotNull
    private String address; //넣을곳

    public Auction toEntity(AuctionRequest auctionRequest) {
        return Auction.builder()
                .quantity(auctionRequest.getQuantity())
                .description(auctionRequest.getDescription())
//                .registeredAt() //경매 시작 시간
//                .endTime(auctionRequest.getEndTime())
                .startPrice(auctionRequest.getStartPrice())
//                .endPrice(auctionRequest.getEndPrice())
//                .limitPrice()
                .shipping(auctionRequest.getShipping())
//                .status() //디폴트:1
//                .modifiedAt()
//                .deletedAt()
//                .winnerPrice()
                .address(auctionRequest.address)
//                .user()
//                .item(item.findByName)
                .build();
    }
}
