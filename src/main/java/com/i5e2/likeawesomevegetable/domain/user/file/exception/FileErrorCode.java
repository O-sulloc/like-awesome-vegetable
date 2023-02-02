package com.i5e2.likeawesomevegetable.domain.user.file.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileErrorCode {
    FARM_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 농가 회원의 이미지를 찾을 수 없습니다."),
    FARM_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 농가 회원의 파일을 찾을 수 없습니다."),
    COMPANY_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 기업 회원의 이미지를 찾을 수 없습니다."),
    COMPANY_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 기업 회원의 파일을 찾을 수 없습니다."),
    FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "보낼 파일이 비어있습니다."),
    FILE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "파일 업로드 용량을 초과했습니다."),
    COMPANY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 기업 정회원은 없습니다."),
    FARM_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 농가 정회원은 없습니다."),
    LOGIN_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 Email로 회원가입 된 회원은 없습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
