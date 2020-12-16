
package ldap;

import org.owasp.encoder.Encode;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LDAPTest5 {
  @PostMapping("/test")
  public boolean test(HttpServletRequest request, String domainName, DirContext ctx)
          throws NamingException {
    String userUnsafe = null;
    String pass = request.getParameter("pass");
    String user = request.getParameter("user");
    userUnsafe = user;
    String tmp = "{1}" + "_USER";
    user = tmp + ".old";
    String saltedPassword = "{0}" + "%!";
    String filter = "(&(uid=" + user + ")(userPassword=" + saltedPassword + "))";

    NamingEnumeration<SearchResult> results =
            ctx.search("ou=system", filter, new String[] {pass, userUnsafe}, new SearchControls());
    return results.hasMore();
  }
}