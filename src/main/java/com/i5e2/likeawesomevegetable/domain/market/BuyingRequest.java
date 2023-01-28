package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyingRequest {
    //TODO : 1.이미지 업로드 2.user매핑 3.category 4.날짜형식 & 유효성 검증 추가

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    //    private LocalDateTime startTime;
//    private LocalDateTime endTime;
    @NotBlank(message = "시작날짜를 달력에서 선택해 주세요")
    private String startTime;
    @NotBlank(message = "종료날짜를 달력에서 선택해 주세요")
    private String endTime;
    @Min(value = 0, message = "카테고리를 선택해 주세요")
    private int category;
    @NotBlank(message = "품종을 선택해 주세요")
    private String item;
    @NotNull(message = "수량을 입력해 주세요")
    @Min(value = 3, message = "3t이상 모집이 가능합니다.")
    @Positive(message = "숫자만 입력해 주세요.")
    private Long quantity;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "정확한 가격을 입력해 주세요")
    private Long price;
    @NotBlank(message = "내용을 입력해 주세요")
    private String description;
    @NotBlank(message = "태그를 입력해 주세요")
    private String tag; //split해서 넣을 곳 ?
    @Min(value = 1, message = "운임 방법을 선택해 주세요")
    private int shipping; //int -> string으로 변환
    @NotBlank(message = "수령인을 입력해 주세요")
    private String receiverName;
    @NotBlank(message = "수령인의 연락처를 입력해 주세요")
    private String receiverPhoneNo;
    @NotBlank(message = "배송 받을 주소를 입력해 주세요")
    private String receiverAddress;
//    private User user;

    public CompanyBuying toEntity(BuyingRequest buyingRequest) {
        return CompanyBuying.builder()
                .title(buyingRequest.getTitle())
                .startTime(buyingRequest.getStartTime())
                .endTime(buyingRequest.getEndTime())
                .category(categoryConvert(buyingRequest.getCategory()))
                .item(buyingRequest.getItem())
                .quantity(buyingRequest.getQuantity())
                .price(buyingRequest.getPrice())
                .description(buyingRequest.getDescription())
                .tag(buyingRequest.getTag())
                .shipping(shippingConvert(buyingRequest.getShipping())) //int->string
                .receiverName(buyingRequest.getReceiverName())
                .receiverPhoneNo(buyingRequest.getReceiverPhoneNo())
                .receiverAddress(buyingRequest.getReceiverAddress())
//                .user()
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
