package com.i5e2.likeawesomevegetable.verification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyEmailRequest {
    private String keyEmail;
    private String emailCode;
}
