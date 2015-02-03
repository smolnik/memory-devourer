package net.adamsmolnik.md;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ASmolnik
 *
 */
@WebServlet({"/go"})
public class GoServlet extends HttpServlet {

    private static final long serialVersionUID = 54532581434354L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Result result;
        String urlAsString = request.getParameter("url");
        try {
            URL url = new URL(urlAsString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            StringBuilder sb = new StringBuilder();
            int numberOfLines = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    if (numberOfLines > 5) {
                        break;
                    }
                    numberOfLines++;
                }
            }
            result = new Result(urlAsString, String.valueOf(con.getResponseCode()), sb.toString());
        } catch (Exception e) {
            result = new Result(urlAsString, e.getLocalizedMessage(), "");
        }
        System.out.println("Result: " + result);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/index.jsp").forward(request, response);;
    }
}
