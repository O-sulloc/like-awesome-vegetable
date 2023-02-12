package com.i5e2.likeawesomevegetable.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendVerifyEmailResponse {
    private String receivedEmail;
    private String message;

    public static SendVerifyEmailResponse of(String receivedEmail, String message) {
        return new SendVerifyEmailResponse(receivedEmail, message);
    }
}
