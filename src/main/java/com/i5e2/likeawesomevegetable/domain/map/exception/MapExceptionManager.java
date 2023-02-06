package com.i5e2.likeawesomevegetable.domain.map.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MapExceptionManager {

    @ExceptionHandler(MapException.class)
    public ResponseEntity<?> mapExceptionHandler(MapException e) {
        return ResponseEntity.status(e.getMapErrorCode().getHttpStatus())
                .body(e.getMessage());
    }
}