package org.zxh.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zxh.pojo.UserPojo;
import org.zxh.service.UserService;

/**
 * Created by zhixinhua on 18/2/2.
 */
@Controller
@AllArgsConstructor
public class HomeController {


    private  UserService userService;

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
    public String doRegister(UserPojo userPojo){
        // 此处省略校验逻辑
        if (userService.insert(userPojo))
            return "redirect:register?success";
        return "redirect:register?error";
    }

}
