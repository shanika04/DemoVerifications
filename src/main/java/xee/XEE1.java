package xee;

import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@Controller
public class XEE1 {
  @PostMapping("/test")
  public String getXml(HttpServletRequest request) throws ParserConfigurationException {
    String xmlStr = request.getParameter("xml");
    Document doc = unsafe(xmlStr);

    if (doc != null) {
      return doc.getElementsByTagName("foo").item(0).getTextContent();
    } else {
      return "XML error";
    }
  }

  private Document unsafe(String xmlString) throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    DocumentBuilder builder;
    try {
      builder = factory.newDocumentBuilder();
      return builder.parse(new InputSource(new StringReader(xmlString)));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}