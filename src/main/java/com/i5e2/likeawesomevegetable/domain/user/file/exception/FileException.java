package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileException extends RuntimeException {
    private FileErrorCode errorCode;
    private String message;
}
