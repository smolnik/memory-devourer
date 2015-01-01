package net.adamsmolnik.healthcheck;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import net.adamsmolnik.fb.Fibonacci;

/**
 * @author ASmolnik
 *
 */
@WebFilter("/hc")
public class HealthCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("OK " + new Date() + ", fibonacci active forks count = " + Fibonacci.getActiveForksCount());
        resp.flushBuffer();
    }

    @Override
    public void destroy() {
    }

}
