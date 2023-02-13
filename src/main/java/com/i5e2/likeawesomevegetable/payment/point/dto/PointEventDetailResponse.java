package com.i5e2.likeawesomevegetable.payment.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointEventDetailResponse {
    private Long pointEventLogId;
    private Long pointTargetUserId;
    private String pointDetailHistory;
    private String pointDetailStatus;
    private Long pointUserId;
    private String pointUserEmail;
    private Long pointEventAmount;
    private LocalDateTime pointUsedEventAt;
}

