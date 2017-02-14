package net.adamsmolnik.log;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * @author ASmolnik
 *
 */
@WebFilter("/*")
public class LogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Date/time: " + LocalDateTime.now() + " request's remoteAddr: " + request.getRemoteAddr() + ", parameter map: "
				+ request.getParameterMap());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
