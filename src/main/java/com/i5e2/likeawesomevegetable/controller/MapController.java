package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.map.AddressService;
import com.i5e2.likeawesomevegetable.domain.map.Positions;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final AddressService addressService;

    @ResponseBody
    @GetMapping("api/v1/company-address")
    public Positions getCompanyAddress() throws IOException, ParseException {
        return addressService.getCompanyAddress();
    }

    @ResponseBody
    @GetMapping("api/v1/farm-address")
    public Positions getFarmAddress() throws IOException, ParseException {
        return addressService.getFarmAddress();
    }

    @GetMapping("/vegetable")
    public String testIndex() {
        return "vegetable/vegetable-index";
    }
}