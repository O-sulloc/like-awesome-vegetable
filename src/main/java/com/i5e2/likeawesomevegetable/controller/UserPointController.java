package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point")
public class UserPointController {
    private UserPointService userPointService;

    @GetMapping("/compare")
    public String readUserPointAndDeposit() {
        return "";
    }
}
