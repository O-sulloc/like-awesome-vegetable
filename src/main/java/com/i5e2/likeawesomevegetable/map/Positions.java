package com.i5e2.likeawesomevegetable.map;

import com.i5e2.likeawesomevegetable.map.dto.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Positions {

    List<AddressInfo> positions;
}

