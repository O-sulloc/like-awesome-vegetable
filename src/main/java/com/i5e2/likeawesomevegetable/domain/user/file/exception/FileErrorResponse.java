package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileErrorResponse {
    private String contents;
}
