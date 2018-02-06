package org.zxh.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zxh.qqfilter.QQAuthenticationFilter;
import org.zxh.qqfilter.QQAuthenticationManager;

import javax.servlet.Filter;


/**
 * Created by xh.zhi on 2018-2-5.
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

        // 在 UsernamePasswordAuthenticationFilter 前添加 QQAuthenticationFilter
        http.addFilterBefore(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private QQAuthenticationFilter qqAuthenticationFilter() {
        QQAuthenticationFilter qqAuthenticationFilter = new QQAuthenticationFilter("/login/qq");
        SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
        simpleUrlAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/user");

        qqAuthenticationFilter.setAuthenticationManager(new QQAuthenticationManager());
        qqAuthenticationFilter.setAuthenticationSuccessHandler(simpleUrlAuthenticationSuccessHandler);

        return qqAuthenticationFilter;
    }
}
