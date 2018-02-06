package src.main.java.org.zxh.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import src.main.java.org.zxh.config.IpAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xh.zhi on 2018-2-5.
 */
//模仿UsernamePasswordAuthenticationFilter
/**
 *
 1、AbstractAuthenticationProcessingFilter是UsernamePasswordAuthenticationFilter的父类，我们的IpAuthenticationProcessingFilter也继承了它
 2、构造器中传入了/ipVerify作为IP登录的端点
 3、attemptAuthentication()方法中加载请求的IP地址，之后交给内部的AuthenticationManager去认证
 */
public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    //使用/ipVerify该端点进行ip认证
    public IpAuthenticationProcessingFilter(){
        super(new AntPathRequestMatcher("/ipVerify"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        //获取host信息
        String host = httpServletRequest.getRemoteHost();
        //交给内部的AuthenticationManager去认证，实现解耦
        return  getAuthenticationManager().authenticate(new IpAuthenticationToken(host));
    }
}
