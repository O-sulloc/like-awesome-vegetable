package com.i5e2.likeawesomevegetable.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CashReceipt {
    private String receiptKey;
    private String type;
    private Integer amount;
    private Integer taxFreeAmount;
    private String issueNumber;
    private String receiptUrl;
}
