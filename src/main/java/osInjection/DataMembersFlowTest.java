package osInjection;

import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.ESAPI;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DataMembersFlowTest {
  @PostMapping("/test")
  public void test(HttpServletRequest request) throws IOException, InterruptedException {
    String command = request.getParameter("command");
    Inner inner = new Inner();
    inner.setSuperInner(encodeForOS(command));
    doStuff("mkdir", inner);
  }

  private void doStuff(String command, Inner inner) throws IOException, InterruptedException {
    Process process = Runtime.getRuntime().exec(command + inner.getSuperInner());
    process.waitFor();
  }

  private String encodeForOS(String param) {
    if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
      return ESAPI.encoder().encodeForOS(new WindowsCodec(), param);
    } else {
      return ESAPI.encoder().encodeForOS(new UnixCodec(), param);
    }
  }

  public static class Inner {
    private String superInner;

    public Inner() {}

    public void setSuperInner(String superInner) {
      this.superInner = superInner;
    }

    public String getSuperInner() {
      return superInner;
    }
  }
}