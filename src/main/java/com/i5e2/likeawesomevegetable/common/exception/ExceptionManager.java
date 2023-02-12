package com.i5e2.likeawesomevegetable.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.user.basic.dto.UserErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    public static void setErrorResponse(HttpServletResponse response, AppErrorCode appErrorCode) throws IOException {
        response.setStatus(appErrorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(
                new ResponseEntity(UserErrorResponse.builder()
                        .contents(appErrorCode.getMessage())
                        .build()
                        , appErrorCode.getStatus())
        ));

    }
}
