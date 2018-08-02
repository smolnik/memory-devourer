package net.adamsmolnik.md;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ASmolnik
 *
 */
@WebServlet({ "/ip" })
public class IpServlet extends HttpServlet {

	private static final long serialVersionUID = 54532581434354L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Optional<Integer> res = Stream.of(request.getCookies()).filter(c->"AWSELB".equals(c.getName())).map(c->c.getMaxAge()).findFirst();
		Optional<String> res1 = Stream.of(request.getCookies()).filter(c->"AWSELB".equals(c.getName())).map(c->c.getValue()).findFirst();
		response.getWriter().println("ip: " + request.getRemoteAddr() + " AWSELB = " + res.orElse(-999) + ", " + res1.orElse("null"));
	}
}
