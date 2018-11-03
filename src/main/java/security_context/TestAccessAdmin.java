package security_context;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//http://localhost:8080/JEE8Security-SecurityContext/testAccess
@WebServlet("/testAccess")
public class TestAccessAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	SecurityContext sc;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>Welcome!</h3>");
		
		UsernamePasswordCredential credentials = new UsernamePasswordCredential("aa", "aa");
		sc.authenticate(request, response, AuthenticationParameters.withParams().credential(credentials));
		
		if(sc.getCallerPrincipal() == null) {
			out.println("Principal is NULL!");
		} else {
			out.println("getCallerPrincipal().getName() -> " + sc.getCallerPrincipal().getName());
		}
		
		out.println("<br/>isCallerInRole('admin') -> "+sc.isCallerInRole("admin"));
		out.println("<br/>isCallerInRole('user') -> "+sc.isCallerInRole("user"));
		
		HttpSession session = request.getSession(false);
		//HttpSession session = request.getSession(true);
		if (session != null) {
			out.println("<br/>Session created: "+ session.getId());
		} else {
			out.println("<br/>Session is NULL");
		}
		
		
		out.println("<br/><br/>hasAccessToWebResource('/secured/basic') (rolesAllowed = 'admin') -> "+sc.hasAccessToWebResource("/secured/basic", "get"));
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
