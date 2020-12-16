
package xss.xssMultiFile;

import org.owasp.encoder.Encode;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class XSSMultiFileSource {
    @PostMapping("/test")
    public void source(HttpServletRequest request) {
        String name = request.getParameter("name");
        new XSSMultiFileSink().sink(Encode.forHtmlUnquotedAttribute(name), request);
    }
}
