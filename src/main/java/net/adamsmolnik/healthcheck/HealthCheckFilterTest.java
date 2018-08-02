package net.adamsmolnik.healthcheck;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ASmolnik
 *
 */
@WebFilter("/hctest")
public class HealthCheckFilterTest implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		long appStartTimestamp = (long) request.getServletContext().getAttribute("appStartTimestamp");
		long now = System.currentTimeMillis();
		long deltaInMins = (now - appStartTimestamp) / 60000;
		int code = deltaInMins > 6 ? HttpServletResponse.SC_OK : HttpServletResponse.SC_SERVICE_UNAVAILABLE;
		resp.setStatus(code);
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("code = " + code + ", deltaInMins = " + deltaInMins);
		resp.flushBuffer();
	}

	@Override
	public void destroy() {
	}

}
