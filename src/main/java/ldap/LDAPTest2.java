package ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LDAPTest2 {
  @PostMapping("/test")
  public boolean test(HttpServletRequest request, DirContext ctx) throws NamingException {
    String pass = request.getParameter("pass");
    String user = request.getParameter("user");
    String filter = "(&(uid=" + "{1}" + ")(userPassword=" + "{0}" + "))";

    NamingEnumeration<SearchResult> results =
            ctx.search("ou=system", filter, new String[] {pass, user}, new SearchControls());
    return results.hasMore();
  }
}