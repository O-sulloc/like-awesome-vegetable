package com.i5e2.likeawesomevegetable.domain.admin.dto;

import com.i5e2.likeawesomevegetable.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminJoinResponse {
    private Long adminId;
    private UserType adminType;
    private String adminName;
    private String adminEmail;
    private LocalDateTime adminRegisteredAt;
}
