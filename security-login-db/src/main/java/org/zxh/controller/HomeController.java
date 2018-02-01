package org.zxh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zxh.pojo.UserPojo;
import org.zxh.service.UserService;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@Controller
//@AllArgsConstructor
public class HomeController {


    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(UserPojo user){
        // 此处省略校验逻辑
        if (userService.insert(user))
            return "redirect:register?success";
        return "redirect:register?error";
    }
}
