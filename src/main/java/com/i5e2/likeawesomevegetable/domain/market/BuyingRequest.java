package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyingRequest {
    //TODO : 1. 후입력(endtime,userid) 2.이미지 업로드 3.리턴타입

    @NotNull(message = "제목을 입력해 주세요")
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotNull(message = "카테고리를 선택해 주세요")
    private String category;
    @NotNull(message = "품종을 선택해 주세요")
    private String item;
    @NotNull(message = "수량을 입력해 주세요")
    @Min(value = 3,message = "3t이상 모집이 가능합니다.")
    @Positive(message = "숫자만 입력해 주세요.")
    private Long quantity;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0,message = "정확한 가격을 입력해 주세요")
    private Long price;
    @NotNull(message = "내용을 입력해 주세요")
    private String description;
    @NotNull(message = "태그를 입력해 주세요")
    private String tag;
    private ShippingEnum shipping;
    @NotNull(message = "수령인을 입력해 주세요")
    private String receiverName;
    @NotNull(message = "수령인의 연락처를 입력해 주세요")
    private String receiverPhoneNo;
    @NotNull(message = "배송 받을 주소를 입력해 주세요")
    private String receiverAddress;
//    private User user;

    public CompanyBuying toEntity(BuyingRequest buyingRequest) {
        return CompanyBuying.builder()
                .title(buyingRequest.getTitle())
                .startTime(buyingRequest.getStartTime())
                .endTime(buyingRequest.getEndTime())
                .category(buyingRequest.getCategory())
                .item(buyingRequest.getItem())
                .quantity(buyingRequest.getQuantity())
                .price(buyingRequest.getPrice())
                .description(buyingRequest.getDescription())
                .tag(buyingRequest.getTag())
                .shipping(buyingRequest.getShipping())
                .receiverName(buyingRequest.getReceiverName())
                .receiverPhoneNo(buyingRequest.getReceiverPhoneNo())
                .receiverAddress(buyingRequest.getReceiverAddress())
//                .user()
                .build();
    }
}
