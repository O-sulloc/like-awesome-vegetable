package com.i5e2.likeawesomevegetable.domain.message.exception;

import com.i5e2.likeawesomevegetable.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessageExceptionManager {
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<?> messageExceptionHandler(MessageException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(
                Result.error(
                        MessageErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .contents(e.getMessage())
                                .build()
                )
        );
    }
}
