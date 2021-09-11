package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping("login")
    private String getLoginPage(){
        return "loginPage";
    }
    @GetMapping("succesfullLogin")
    private String getRedirectPage(){
        return "redirectPage";
    }

}
