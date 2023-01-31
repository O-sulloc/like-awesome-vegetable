package com.i5e2.likeawesomevegetable.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLogoutResponse {
    private String logoutResult;
}
