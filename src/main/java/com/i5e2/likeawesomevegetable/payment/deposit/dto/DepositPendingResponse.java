package com.i5e2.likeawesomevegetable.payment.deposit.dto;

import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositPendingResponse {
    private Long userPointDepositId;
    private Long pointTotalBalance;
    private Long depositTotalBalance;
    private Long depositTargetPostId;
    private DepositStatus depositStatus;
    private Long depositCommission;
    private String depositType;
    private LocalDateTime depositPendingAt;
    private PostPointActivateEnum targetPostActivateStatus;
}
