package com.i5e2.likeawesomevegetable.domain.contract;

import lombok.Getter;

@Getter
public enum ContractStatus {
    CREATED,
    COMPANY_SIGN_WAITING,
    FARMER_SIGN_WAITING,
    COMPLETED
}
