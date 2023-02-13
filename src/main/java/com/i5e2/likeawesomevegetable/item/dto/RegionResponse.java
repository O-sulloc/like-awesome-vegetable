package com.i5e2.likeawesomevegetable.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RegionResponse {
    String regionName;
    List<RegionAverage> regionAverages;

    public static RegionResponse makeRegionResponse(String region, List<RegionAverage> regionAverages) {
        return new RegionResponse(region, regionAverages);
    }
}
