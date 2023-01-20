package com.i5e2.likeawesomevegetable.domain.user;

import lombok.Getter;

@Getter
public enum UserType {
    COMPANY, // 0
    FARM, // 1
    VERIFIED_COMPANY, // 2
    VERIFIED_FARM, // 3
    ADMIN // 4
}
