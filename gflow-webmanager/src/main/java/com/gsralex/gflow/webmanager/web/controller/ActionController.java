package com.gsralex.gflow.webmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@Controller
@RequestMapping("/action")
public class ActionController {

    @RequestMapping("/list")
    public String list() {
        return "actionList";
    }
}
