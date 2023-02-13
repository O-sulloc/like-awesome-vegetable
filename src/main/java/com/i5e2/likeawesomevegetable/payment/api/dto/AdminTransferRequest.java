package com.i5e2.likeawesomevegetable.payment.api.dto;

import lombok.Data;

@Data
public class AdminTransferRequest {
    private String transferUserName;
    private String accountNumber;
    private String bankName;
    private Long transferAmount;

    private Long amount;
    private String orderId;
    private String orderName;
}
