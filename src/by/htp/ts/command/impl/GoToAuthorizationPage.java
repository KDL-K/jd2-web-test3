package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToAuthorizationPage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		session.setAttribute("CommandImplParameter.GOTO_REQUEST", "/WEB-INF/jsp/authorization.jsp");
		request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp").forward(request, response);
		
	}
	

}
