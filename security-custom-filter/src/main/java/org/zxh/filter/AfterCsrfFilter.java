package src.main.java.org.zxh.filter;

import org.apache.catalina.servlet4preview.GenericFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by zhixinhua on 18/2/3.
 */
public class AfterCsrfFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("This is a filter after CsrfFilter.");
        // 继续调用 Filter 链
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("执行AfterCsrfFilter!destroy");
    }
}
