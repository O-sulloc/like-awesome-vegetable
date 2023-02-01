package com.i5e2.likeawesomevegetable.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthLoginResponse {
    private String jwt;
}
