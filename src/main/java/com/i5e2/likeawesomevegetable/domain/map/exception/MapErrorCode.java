package com.i5e2.likeawesomevegetable.domain.map.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MapErrorCode {
    COMPANY_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "기업 주소가 존재하지않습니다."),
    FARM_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "농가 주소가 존재하지않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}