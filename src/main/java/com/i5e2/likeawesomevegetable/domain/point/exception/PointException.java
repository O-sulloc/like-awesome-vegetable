package com.i5e2.likeawesomevegetable.domain.point.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointException extends RuntimeException {
    private PointErrorCode errorCode;
    private String message;

    @Override
    public String toString() {
        if (message == null) return errorCode.getMessage();
        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
