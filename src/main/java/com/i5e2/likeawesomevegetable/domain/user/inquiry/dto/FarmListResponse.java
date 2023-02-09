package com.i5e2.likeawesomevegetable.domain.user.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmListResponse {

    private Long id;
    private String farmOwnerName;
    private String farmMajorItem;
    private String farmAddress;
}
