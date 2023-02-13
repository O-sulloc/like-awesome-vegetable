package com.i5e2.likeawesomevegetable.user.basic.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserErrorResponse {
    private String contents;
}
