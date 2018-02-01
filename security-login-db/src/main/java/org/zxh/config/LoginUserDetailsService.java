package org.zxh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zxh.pojo.UserPojo;
import org.zxh.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {


    private final UserService userService;

    @Autowired
    public LoginUserDetailsService(UserService userService){
        this.userService = userService;
    }

    //用户加载信息
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserPojo user = userService.getUserByName(userName);
        if(user==null)
            new UsernameNotFoundException("没有找到用户！");

        //Collection<? extends GrantedAuthority> authorities
        UserDetails userDetails = new User(user.getUsername(),user.getPassword(),createAuthorities(user.getRoles()));
        return userDetails;
    }

    /**
     * 权限字符串转化
     *
     * 如 "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     *
     * @param roleStr 权限字符串
     */
    private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return simpleGrantedAuthorities;
    }
}
