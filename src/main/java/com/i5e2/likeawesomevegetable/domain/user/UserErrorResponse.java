package com.i5e2.likeawesomevegetable.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserErrorResponse {
    private String contents;
}
