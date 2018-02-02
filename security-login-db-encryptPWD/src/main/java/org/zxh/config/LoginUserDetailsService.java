package org.zxh.config;

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
 * Created by zhixinhua on 18/2/2.
 */
@Service
public class LoginUserDetailsService  implements UserDetailsService{

    private final UserService userService;

    public LoginUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserPojo userPojo = userService.getByUsername(userName);
        if(userPojo==null)
            throw new UsernameNotFoundException("用户不存在！");

        //Collection<? extends GrantedAuthority> authorities
        List<SimpleGrantedAuthority> simpleGrantedAuthoritys = createAuthorities(userPojo.getRoles());
        return new User(userPojo.getUsername(),userPojo.getPassword(),simpleGrantedAuthoritys);

    }

    /**
     * 权限字符串转化
     *
     * 如 "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     *
     * @param roleStr 权限字符串
     */
    private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        List<SimpleGrantedAuthority> simpleGrantedAuthoritys = new ArrayList<SimpleGrantedAuthority>();
        String[] roles = roleStr.split(",");
        for (String role : roles){
            simpleGrantedAuthoritys.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthoritys;
    }
}
