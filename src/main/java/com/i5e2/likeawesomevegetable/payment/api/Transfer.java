package com.i5e2.likeawesomevegetable.payment.api;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private String bankCode;
    private String settlementStatus;
}
