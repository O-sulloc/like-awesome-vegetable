package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCardDto {
    private Long amount; //카드로 결제한 금액
    private String issuerCode; //카드사 숫자 코드
    private String acquirerCode; //카드 매입사 숫자 코드
    private String number; //카드 번호
    private Integer installmentPlanMonths; //할부 개월 수
    private String approveNo; //카드사 승인 번호
    private boolean useCardPoint; //카드사 포인트 사용여부
    private String cardType; //카드 종류 신용,체크,기프트
    private String ownerTYpe; //카드 소유자 타입 개인,법인
    private String acquireStatus; //카드 결제 매입 상태
    private boolean isInterestFree; //무이자 할부 적용 여부
    private String interestPayer; //할부 수수료 부담 주체
}
