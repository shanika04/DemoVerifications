package pathTraversal;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PTTest1 {
  private final String BASE_DIRECTORY = "/users/ws/profiles/";

  @PostMapping("/test")
  public void test(HttpServletRequest request) {
    try {
      String fileName = request.getParameter("fileName");
      File myFile = new File(BASE_DIRECTORY + fileName);
      if (myFile
              .getCanonicalPath()
              .startsWith(new File(BASE_DIRECTORY + "fileName").getCanonicalPath())) {
        throw new Exception("invalid file path entered");
      }
      deleteFile(myFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void deleteFile(File f) {
    f.delete();
  }
}