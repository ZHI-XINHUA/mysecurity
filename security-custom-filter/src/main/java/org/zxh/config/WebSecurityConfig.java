package src.main.java.org.zxh.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import src.main.java.org.zxh.filter.AfterCsrfFilter;
import src.main.java.org.zxh.filter.BeforeLoginFilter;

/**
 * Created by zhixinhua on 18/2/3.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        // 在 UsernamePasswordAuthenticationFilter 前添加 BeforeLoginFilter
        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        // 在 CsrfFilter 后添加 AfterCsrfFilter
        http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);
    }

    /**
     * 在内存中创建一个名为 "zxh" 的用户，密码为 "123456"，拥有 "USER" 权限
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zxh")
                .password("123456")
                .roles("USER");
    }
}
