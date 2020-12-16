package osInjection;

import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.WindowsCodec;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OSI {
  @PostMapping("/test")
  public void runCommand(HttpServletRequest request) throws IOException, InterruptedException {
    String command = request.getParameter("command");
    test(encodeForOS(command), "");
  }

  public void test(String data1, String data2) throws IOException, InterruptedException {
    String osCommand;
    String uuid = UUID.randomUUID().toString();
    if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
      osCommand = "c:\\WINDOWS\\SYSTEM32\\cmd.exe /c dir ";
    } else {
      osCommand = "/bin/ls ";
    }
    String newData = data1 + uuid + data2;

    Process process = Runtime.getRuntime().exec(osCommand + newData);
    process.waitFor();
  }

  private String encodeForOS(String param) {
    if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
      return ESAPI.encoder().encodeForOS(new WindowsCodec(), param);
    } else {
      return ESAPI.encoder().encodeForOS(new UnixCodec(), param);
    }
  }
}