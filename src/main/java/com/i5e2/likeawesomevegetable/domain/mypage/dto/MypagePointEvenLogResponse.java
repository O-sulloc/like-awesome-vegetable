package com.i5e2.likeawesomevegetable.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypagePointEvenLogResponse {
    private Long pointEventLogId;
    private String paymentMethod;
    private String paymentType;
    private String paymentRequestedAt;
    private String pointEventStatus;
    private String pointEventHistory;
    private Long pointTargetUserId;
    private Long pointEventAmount;
    private String pointRequestAt;
    private String pointApprovedAt;
    private Long pointUserId;
    private LocalDateTime pointUsedEventAt;
}
