package com.i5e2.likeawesomevegetable.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{
    private UserErrorCode errorCode;
    private String message;
}
