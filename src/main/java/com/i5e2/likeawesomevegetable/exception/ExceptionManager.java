package com.i5e2.likeawesomevegetable.exception;

import com.i5e2.likeawesomevegetable.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(AwesomeVegeAppException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Result.error(e.getMessage()));
    }

    @ExceptionHandler(AwesomeVegeAppException.class)
    public ResponseEntity<?> AwesomeAppExceptionHandler(AwesomeVegeAppException e) {
        ErrorResult errorResult = new ErrorResult(e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Result.error(errorResult));
    }

    // 파일 업로드 용량 초과시 에러처리
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<?> handleMaxUploadSizeExceededException(AwesomeVegeAppException e) {
        ErrorResult errorResult = new ErrorResult(e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Result.error(errorResult));
    }

}
