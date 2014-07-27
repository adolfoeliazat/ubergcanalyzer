package de.morten.identityaccess;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(value="/logout", name="logout-servlet")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		final HttpSession session = req.getSession(false);
		if(session != null) session.invalidate();
		
		resp.sendRedirect(req.getContextPath());
		
	}
}
