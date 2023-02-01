package com.i5e2.likeawesomevegetable.domain.user.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FarmFileResponse {
    private String fileName;
    private String message;

    public static FarmFileResponse of(String uploadFileName, String message) {
        return new FarmFileResponse(uploadFileName, message);
    }
}
