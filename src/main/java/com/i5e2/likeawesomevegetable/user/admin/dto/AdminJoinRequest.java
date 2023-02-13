package com.i5e2.likeawesomevegetable.user.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminJoinRequest {
    @NotBlank(message = "관리자명은 필수 입력 값입니다.")
    private String adminName;
    @NotBlank(message = "연락처는 필수 입력 값입니다.")
    private String adminPhoneNo;
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String adminEmail;
    private String adminNickname;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String adminPassword;
}
