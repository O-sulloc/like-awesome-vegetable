package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD,
    MONEY_TRANSFER, VIRTUAL_TRANSFER
}
