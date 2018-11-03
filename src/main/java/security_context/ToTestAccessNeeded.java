package security_context;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.security.DeclareRoles;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//http://localhost:8080/JEE8Security-SecurityContext/secured/basic
@DeclareRoles({ "admin", "user", "foo" })
@WebServlet("/secured/basic")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))

public class ToTestAccessNeeded extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	SecurityContext sc;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>Welcome!</h3>");
		
	}


}
