package com.cisu.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// A bean is an object that manage by the spring framework

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "index.html";
    }
}
