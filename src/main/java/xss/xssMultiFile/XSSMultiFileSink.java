package xss.xssMultiFile;

import org.owasp.encoder.Encode;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XSSMultiFileSink {
    public void sink(String name, HttpServletRequest request) {
        String email = request.getParameter("email");
        String vul = Encode.forHtmlUnquotedAttribute(email) + name;
        try {
            PrintWriter out = ((HttpServletResponse) null).getWriter();
            out.write("<br><br>Unsafe quoted attribute:<br>");
            out.write("<button value=\"" + vul + "\">Unsafe quoted attribute</button>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}