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
public class XSS1 {
  HttpServletRequest request;
  HttpServletResponse response;

  @PostMapping("/test")
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    doGet(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    this.request = request;
    this.response = response;
    String name = request.getParameter("name");
    unsafe(Encode.forCssString(name));
  }

  private void unsafe(String name) {
    try {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.write("<div> <style>color:" + name + "</style>");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
