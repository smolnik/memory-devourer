package net.adamsmolnik.md;

import java.io.IOException;

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
		response.getWriter().println("ip: " + request.getRemoteAddr());
	}
}
