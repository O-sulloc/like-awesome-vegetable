package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionManager {
    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> appExceptionHandler(FileException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(
                FileErrorResponse.builder()
                        .contents(e.getMessage())
                        .build()
        );
    }
}
