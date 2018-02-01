package org.zxh.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig!configure 》》 security拦截配置");
        http.authorizeRequests() //http.authorizeRequests()方法有多个子节点，每个macher按照他们的声明顺序执行。
                .antMatchers("/").permitAll() //任何用户都可以访问URL以"/"
                .antMatchers("/user/**").hasRole("USER") //以 "/user/" 开头的URL只能由拥有 "ROLE_USER"角色的用户访问。请注意我们使用 hasRole 方法，没有使用 "ROLE_" 前缀.
                .and()
                .formLogin().loginPage("/login")//登录地址（匹配登录的form表单地址）
                .defaultSuccessUrl("/user")//登录成功的跳转页面
                .and()
                .logout().logoutUrl("/logout")//退出登录的地址（匹配退出的form表单地址）
                .logoutSuccessUrl("/login");//退出成功跳转页面
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() //内存中的身份验证
                .withUser("admin").password("123456").roles("USER"); //在内存中为USER角色添加admin 用户
    }
}
