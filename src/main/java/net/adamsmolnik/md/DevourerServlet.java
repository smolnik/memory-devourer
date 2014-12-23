package net.adamsmolnik.md;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ASmolnik
 *
 */
@WebServlet({"/eat"})
public class DevourerServlet extends HttpServlet {

    private static final long serialVersionUID = 1453454354354L;

    private List<byte[]> devourer = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.append(new InstanceMetadata().fetch());
        writer.append("\n\n");

        String yum = request.getParameter("yum");
        String clear = request.getParameter("clear");

        if ((yum != null) && (!"".equals(yum.trim()))) {
            this.devourer.add(new byte[Integer.valueOf(yum).intValue() * 1024 * 1024]);
            writer.println("Consumed: " + yum + " MB");
        }
        if ("ok".equals(clear)) {
            this.devourer.clear();
            System.gc();
            writer.println("Cleared");
            System.gc();
            writer.println("\n After GC freeMemory: " + Runtime.getRuntime().freeMemory());
        }
        response.flushBuffer();
    }
}
