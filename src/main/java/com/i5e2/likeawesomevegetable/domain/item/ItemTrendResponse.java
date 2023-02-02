package com.i5e2.likeawesomevegetable.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemTrendResponse {

    private String date;
    private String max;
    private String min;
}