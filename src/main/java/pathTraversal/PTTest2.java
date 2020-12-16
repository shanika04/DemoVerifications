package pathTraversal;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PTTest2 {
  private final String BASE_DIRECTORY = "/users/ws/profiles/";

  @PostMapping("/test")
  public void test(HttpServletRequest request) {
    String fileName = request.getParameter("fileName");
    sink(fileName);
  }

  private void sink(String filename) {
    File myFile = new File(BASE_DIRECTORY, filename);
    try {
      if (myFile.getCanonicalPath().startsWith(new File("/users/ws/profiles/").getCanonicalPath())) {
        throw new Exception("invalid file path entered");
      }
      myFile.delete();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}