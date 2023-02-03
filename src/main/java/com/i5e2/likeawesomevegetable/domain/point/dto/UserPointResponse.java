package com.i5e2.likeawesomevegetable.domain.point.dto;

import com.i5e2.likeawesomevegetable.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPointResponse {
    private Long userPointId;
    private Long userTotalBalance;
    private Long userId;
    private String managerName;
    private UserType userType;

    @Builder
    public UserPointResponse(Long userPointId, Long userTotalBalance, Long userId, String managerName, UserType userType) {
        this.userPointId = userPointId;
        this.userTotalBalance = userTotalBalance;
        this.userId = userId;
        this.managerName = managerName;
        this.userType = userType;
    }
}
