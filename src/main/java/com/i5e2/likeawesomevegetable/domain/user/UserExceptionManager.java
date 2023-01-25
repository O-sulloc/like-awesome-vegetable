package com.i5e2.likeawesomevegetable.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class UserExceptionManager {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> AppExceptionHandler(UserException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(
                UserErrorResponse.builder()
                        .contents(e.getMessage())
                        .build()
                );
    }

    public static void setErrorResponse(HttpServletResponse response, UserErrorCode userErrorCode) throws IOException {
        response.setStatus(userErrorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(
                new ResponseEntity(UserErrorResponse.builder()
                        .contents(userErrorCode.getMessage())
                        .build()
                , userErrorCode.getStatus())
        ));

    }
}
