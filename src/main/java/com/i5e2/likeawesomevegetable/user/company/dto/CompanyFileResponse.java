package com.i5e2.likeawesomevegetable.user.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompanyFileResponse {
    private String fileName;
    private String message;

    public static CompanyFileResponse of(String uploadFileName, String message) {
        return new CompanyFileResponse(uploadFileName, message);
    }
}
