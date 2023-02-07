package com.i5e2.likeawesomevegetable.domain.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBuyingResponse {
    private String fileName;
    private String message;

    public static CompanyBuyingResponse of(String fileName, String message){
        return new CompanyBuyingResponse(fileName,message);
    }
}
