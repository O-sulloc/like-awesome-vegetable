package com.i5e2.likeawesomevegetable.domain.apply.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplyExceptionManager {

    @ExceptionHandler(ApplyException.class)
    public ResponseEntity<?> appExceptionHandler(ApplyException e) {
        return ResponseEntity.status(e.getApplyErrorCode().getHttpStatus())
                .body(e.getMessage());
    }
}
