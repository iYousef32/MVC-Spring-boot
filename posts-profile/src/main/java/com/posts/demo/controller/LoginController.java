package com.posts.demo.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {

    @GetMapping("/showLoginForm")
    public String showLoginForm(){

        return "login-form";
    }
}
