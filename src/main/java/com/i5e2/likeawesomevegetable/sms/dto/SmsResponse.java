package com.i5e2.likeawesomevegetable.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {

    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
