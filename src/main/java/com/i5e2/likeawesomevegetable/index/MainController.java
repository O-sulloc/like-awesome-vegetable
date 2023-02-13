package com.i5e2.likeawesomevegetable.index;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api("Main Controller")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @ApiOperation(value = "메인화면 요청", notes = "서비스의 메인 화면을 불러온다.")
    @GetMapping("/")
    public String showIndex() {
        return "main/index";
    }

}
