package com.i5e2.likeawesomevegetable.domain.verification.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerificationErrorResponse {
    private VerificationErrorCode errorCode;
    private String contents;
}
