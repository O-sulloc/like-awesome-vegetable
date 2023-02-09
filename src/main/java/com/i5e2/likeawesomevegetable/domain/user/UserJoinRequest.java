package com.i5e2.likeawesomevegetable.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String email;
    private String password;
    private String managerName;
    private String phoneNo;
}
