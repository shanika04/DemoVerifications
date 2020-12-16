package pathTraversal;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PTTest3 {
  private final String BASE_DIRECTORY = "/users/ws/profiles/";

  @PostMapping("/test")
  public void test(HttpServletRequest request, String customDirName) {
    try {
      String fileName = request.getParameter("fileName");
      String dirPath = getValue(BASE_DIRECTORY) + customDirName + "subsubdir/";
      File myFile = new File(dirPath + fileName);
      if (myFile.getCanonicalPath().startsWith(new File(dirPath + "fileName").getCanonicalPath())) {
        throw new Exception("invalid file path entered");
      }
      myFile.delete();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String getValue(String str) {
    return str + "userdir/";
  }
}
