package com.i5e2.likeawesomevegetable.domain.map.exception;

import com.i5e2.likeawesomevegetable.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AddressExceptionManager {

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<?> mapExceptionHandler(AddressException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Result.error(new AddressErrorResponse(e.getErrorCode(), e.getMessage())));
    }
}