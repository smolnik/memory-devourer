package net.adamsmolnik.fb;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ASmolnik
 *
 */
@WebServlet({"/fb"})
public class FbServlet extends HttpServlet {

    private static final long serialVersionUID = 8690521606291963042L;

    private static final int CACHE_DURATION_IN_SECOND = 60 * 10;

    private static final long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND * 1000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nAsString = request.getParameter("n");
        String result;
        try {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            Fibonacci fb = new Fibonacci(Integer.parseInt(nAsString));
            result = "fb(" + nAsString + ") = " + forkJoinPool.invoke(fb) + ", timestamp: " + new Date();
        } catch (Exception e) {
            result = "Exception raised " + e.getLocalizedMessage();
        }
        response.setStatus(HttpServletResponse.SC_OK);
        long now = System.currentTimeMillis();
        response.addHeader("Cache-Control", "max-age=" + CACHE_DURATION_IN_SECOND);
        response.addHeader("Cache-Control", "public, must-revalidate");
        response.setDateHeader("Last-Modified", now);
        response.setDateHeader("Expires", now + CACHE_DURATION_IN_MS);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(result);
        response.flushBuffer();
    }

}
