package xss;

import org.owasp.encoder.Encode;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropagatedSourcesTest {
    HttpServletRequest request;
    HttpServletResponse response;

    @PostMapping("test")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        String pass = request.getParameter("pass");
        unsafe(pass);
    }

    private void unsafe(String pass) {
        try {
            String name;
            if (pass.contains("p")) {
                name = request.getParameter("name");
            } else {
                if (pass.contains("a")) {
                    name = request.getParameter("area");
                } else {
                    name = request.getParameter("pass");
                }
            }
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            name = Encode.forCssUrl(name) + "constant";
            out.write("<div style=\"background-image: url('" + name + "')\">");
            out.write("<br><br>Unsafe CSS style attribute context:<br>");
            out.write("<h1>TEXT</h1>");
            out.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}