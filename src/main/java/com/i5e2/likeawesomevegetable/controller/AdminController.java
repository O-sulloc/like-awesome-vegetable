package com.i5e2.likeawesomevegetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public String adminIndex() {
        return "admin/admin-index";
    }

    @GetMapping("/transfer")
    public String checkAdminTransferInfo() {
        return "admin/admin-payment";
    }
    
}
