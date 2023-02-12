package com.i5e2.likeawesomevegetable.map.controller;

import com.i5e2.likeawesomevegetable.map.Positions;
import com.i5e2.likeawesomevegetable.map.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@Api("MapController")
@RequiredArgsConstructor
public class MapController {

    private final AddressService addressService;

    @ResponseBody
    @ApiOperation(value = "기업 주소 좌표 변환",
            notes = "마커에 찍을 기업 주소를 좌표로 변환하여 기업 id와 기업명과 함께 반환한다.")
    @GetMapping("api/v1/company-address")
    public Positions getCompanyAddress() throws IOException, ParseException {
        return addressService.getCompanyAddress();
    }

    @ResponseBody
    @ApiOperation(value = "농가 주소 좌표 변환",
            notes = "마커에 찍을 농가 주소를 좌표로 변환하여 농가 id와 농가명과 함께 반환한다.")
    @GetMapping("api/v1/farm-address")
    public Positions getFarmAddress() throws IOException, ParseException {
        return addressService.getFarmAddress();
    }

    @ApiOperation(value = "지도가 나올 view 반환",
            notes = "지도가 나올 view를 반환한다.")
    @GetMapping("/vegetable")
    public String testIndex() {
        return "vegetable/vegetable-index";
    }
}