package XPath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class XPathTest2 {
    @PostMapping("/test")
    public String authenticate(String user, String pass) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/users/user[@name='" + "$user" + "' and @pass='" + "$pass" + "']";
        try {
            if (!user.isEmpty()) {
                xPath.setXPathVariableResolver(
                        v -> {
                            switch (v.getLocalPart()) {
                                case "user":
                                    return user;
                                case "pass":
                                    return pass;
                                default:
                                    throw new IllegalArgumentException();
                            }
                        });
                return xPath.evaluate(expression, "null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}