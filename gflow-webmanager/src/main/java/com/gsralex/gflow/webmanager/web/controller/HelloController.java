package com.gsralex.gflow.webmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gsralex
 * @version 2018/8/29
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "index";
    }
}
