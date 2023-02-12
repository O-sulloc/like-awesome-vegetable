package com.i5e2.likeawesomevegetable.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserResponse {
    private String userEmail;
    private String verifiedName;
    private String message;

    public static VerifyUserResponse of(String userEmail, String verifiedName, String message) {
        return new VerifyUserResponse(userEmail, verifiedName, message);
    }
}
