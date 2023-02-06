package com.i5e2.likeawesomevegetable.domain.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferEventDetailResponse {
    private Long transferEventLogId;
    private Long transferTargetUserId;
    private String transferDetailHistory;
    private String transferDetailStatus;
    private Long transferUserId;
    private Long transferEventAmount;
    private LocalDateTime transferUsedEventAt;
}
