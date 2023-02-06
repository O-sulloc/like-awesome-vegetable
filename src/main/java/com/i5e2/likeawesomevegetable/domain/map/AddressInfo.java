package com.i5e2.likeawesomevegetable.domain.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {

    private Long id;
    private String name;
    private String x;
    private String y;
}
