package com.i5e2.likeawesomevegetable.domain.market;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRequest {
    //TODO : 1. 후입력(price,time,userid,time) 2.이미지 업로드 3.category 다양화 4.날짜형식 & 유효성 검증 추가 5.limit

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
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
    private Integer quantity;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "정확한 가격을 입력해 주세요")
    private Integer startPrice;
    @NotNull(message = "가격을 입력해 주세요")
    @Min(value = 0, message = "정확한 가격을 입력해 주세요")
    private Integer limitPrice;

    private List<MultipartFile> uploadImages;

//    private User user;

    public FarmAuction toEntity(AuctionRequest auctionRequest) {
        return FarmAuction.builder()
                .auctionTitle(auctionRequest.getTitle())
                .auctionStartTime(auctionRequest.getRegisteredAt())
                .auctionEndTime(auctionRequest.getEndTime())
                .auctionItemCategory(auctionRequest.getCategory())
                .auctionItem(auctionRequest.getItem())
                .auctionQuantity(auctionRequest.getQuantity())
                .auctionStartPrice(auctionRequest.getStartPrice())
//                .auctionHighestPrice() 종료가격 입력
                .auctionLimitPrice(auctionRequest.getLimitPrice())
                .auctionDescription(auctionRequest.getDescription())
//                .auctionTag() 추가추가
//                .auctionShipping()
//                .auctionStatus()
//                .auctionRegisteredAt
//                .auctionModifiedAt()
//                .auctionDeletedAt()
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

}
