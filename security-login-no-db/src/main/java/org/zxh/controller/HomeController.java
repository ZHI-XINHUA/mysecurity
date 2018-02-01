package org.zxh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@Controller
public class HomeController {

    @GetMapping({"/","/index","/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
