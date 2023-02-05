package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileErrorResponse {
    private FileErrorCode errorCode;
    private String contents;

    public FileErrorResponse(FileErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
