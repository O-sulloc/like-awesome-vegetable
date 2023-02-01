package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    // 최근일자 도·소매 가격정보
    @GetMapping("/item")
    public ResponseEntity<?> PriceInfo() throws ParseException {

        return ResponseEntity.ok().body(itemService.priceInfo());
    }
}
