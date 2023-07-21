package com.market.demo.simulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginForm")
    public String showLoginForm(){

        //return "plain-login";
        return "fancy-login";
    }

    @GetMapping("/access-Denied")
    public String showAccessDeniedPage(){


        return "access-Denied";
    }
}
