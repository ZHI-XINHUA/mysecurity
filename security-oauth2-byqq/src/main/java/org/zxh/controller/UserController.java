package org.zxh.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zxh.pojo.QQUser;

/**
 * Created by xh.zhi on 2018-2-5.
 */
@Controller
public class UserController {

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal  UsernamePasswordAuthenticationToken userAuthentication, Model model){
        QQUser qqUser = (QQUser) userAuthentication.getPrincipal();
        model.addAttribute("username",qqUser.getNickname());
        model.addAttribute("avatar",qqUser.getAvatar());
        return "user/user";
    }
}
