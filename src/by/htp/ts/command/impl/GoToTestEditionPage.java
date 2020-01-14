package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToTestEditionPage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		d
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page");
			return;
		}else {
			session.setAttribute("goto_request", "/WEB-INF/jsp/testedition.jsp");
		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/testedition.jsp").forward(request, response);
	}

}
