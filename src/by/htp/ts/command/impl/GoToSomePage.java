package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToSomePage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please Sign in");
			return;
		}
		String requestMessage = (String)session.getAttribute(CommandImplParameter.MESSAGE);
		if(requestMessage != null) {
			request.setAttribute(CommandImplParameter.MESSAGE_ATTR, requestMessage);
			session.removeAttribute(CommandImplParameter.MESSAGE);
		}
		
		String gotoRequest = (String)session.getAttribute(CommandImplParameter.GOTO_REQUEST);
		System.out.println("from some page "+gotoRequest);
		
		request.getRequestDispatcher(gotoRequest).forward(request, response);
	}

}
