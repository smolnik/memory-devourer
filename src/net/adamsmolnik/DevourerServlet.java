package net.adamsmolnik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/eat"})
public class DevourerServlet extends HttpServlet {

    private static final long serialVersionUID = 1453454354354L;

    private List<byte[]> devourer = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String yum = request.getParameter("yum");
        String clear = request.getParameter("clear");

        PrintWriter writer = response.getWriter();
        if ((yum != null) && (!"".equals(yum.trim()))) {
            this.devourer.add(new byte[Integer.valueOf(yum).intValue() * 1024 * 1024]);
            writer.println("Consumed: " + yum + " MB");
        }
        if ("ok".equals(clear)) {
            this.devourer.clear();
            System.gc();
            writer.println("Cleared");
            System.gc();
            writer.append("\n After GC freeMemory: " + Runtime.getRuntime().freeMemory());
        }
        response.flushBuffer();
    }
}
