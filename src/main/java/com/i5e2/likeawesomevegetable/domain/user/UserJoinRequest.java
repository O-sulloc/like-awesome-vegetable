package com.i5e2.likeawesomevegetable.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserJoinRequest {
    private String email;
    private String businessName;
    private String password;
    private String managerName;
    private String phoneNo;
    private String address;
    private UserType verification;
    private String businessNo;
}
