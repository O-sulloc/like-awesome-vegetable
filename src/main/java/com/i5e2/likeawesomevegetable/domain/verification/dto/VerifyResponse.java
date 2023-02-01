package com.i5e2.likeawesomevegetable.domain.verification.dto;

import com.i5e2.likeawesomevegetable.domain.verification.Verification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResponse {
    private Verification status;
    private String message;

    public static VerifyResponse of(Verification status, String message) {
        return new VerifyResponse(status, message);
    }
}
