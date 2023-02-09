package com.i5e2.likeawesomevegetable.domain.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBuyingImageResponse {
    private String fileName;
    private String message;

    public static CompanyBuyingImageResponse of(String fileName, String message){
        return new CompanyBuyingImageResponse(fileName,message);
    }
}
