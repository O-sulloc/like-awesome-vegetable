package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRequest {
    //TODO : 1. 후입력(price,time,userid,time) 2.이미지 업로드 3.category 다양화 4.날짜형식 & 유효성 검증 추가 5.limit

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    //    private LocalDateTime startTime;
//    private LocalDateTime endTime;
    @NotBlank(message = "시작날짜를 달력에서 선택해 주세요")
    private String registeredAt;
    @NotBlank(message = "종료날짜를 달력에서 선택해 주세요")
    private String endTime;
    @NotBlank(message = "품종을 선택해 주세요")
    private String item;
    @Min(value = 1, message = "카테고리를 선택해 주세요")
    private int category;
    @NotBlank(message = "내용을 입력해 주세요")
    private String description;
    @NotNull(message = "수량을 입력해 주세요")
    @Min(value = 3, message = "3t이상 모집이 가능합니다.")
    @Positive(message = "숫자만 입력해 주세요.")
    private Long quantity;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "정확한 가격을 입력해 주세요")
    private Long startPrice;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "정확한 가격을 입력해 주세요")
    private Long limitPrice;

//    private User user;

    public FarmAuction toEntity(AuctionRequest auctionRequest) {
        return FarmAuction.builder()
                .title(auctionRequest.getTitle())
                .registeredAt(auctionRequest.getRegisteredAt())
                .endTime(auctionRequest.getEndTime())
                .category(categoryConvert(auctionRequest.getCategory()))
                .item(auctionRequest.getItem())
                .quantity(auctionRequest.getQuantity())
                .startPrice(auctionRequest.getStartPrice())
//                .endPrice()
                .limitPrice(auctionRequest.getLimitPrice())
                .description(auctionRequest.getDescription())
//                .shipping()
//                .status()
//                .modifiedAt()
//                .deletedAt()
//                .winnerPrice()
//                .address()
                .build();
    }

    private String shippingConvert(int value) {
        if (value == 1) {
            return "BOXING";
        } else if (value == 2) {
            return "TONBAG";
        } else if (value == 3) {
            return "CONTIBOX";
        }
        return "0";
    }

    private String categoryConvert(int value) {
        if (value == 1) {
            return "과일";
        } else if (value == 2) {
            return "채소";
        }
        return "0";
    }
}
