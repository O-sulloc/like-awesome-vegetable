package com.i5e2.likeawesomevegetable.domain.map.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressErrorResponse {
    AddressErrorCode ErrorCode;
    String message;
}