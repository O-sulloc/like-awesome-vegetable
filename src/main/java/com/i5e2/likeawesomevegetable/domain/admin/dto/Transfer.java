package com.i5e2.likeawesomevegetable.domain.admin.dto;

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
