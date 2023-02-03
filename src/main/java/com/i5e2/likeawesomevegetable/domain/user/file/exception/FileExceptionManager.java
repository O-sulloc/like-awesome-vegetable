package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import com.i5e2.likeawesomevegetable.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionManager {
    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> appExceptionHandler(FileException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(
                Result.error(
                        FileErrorResponse.builder()
                                .contents(e.getMessage())
                                .build()
                )
        );
    }
}
