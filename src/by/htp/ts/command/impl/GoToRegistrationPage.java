package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToRegistrationPage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GoToRegistrationPage execute");
		HttpSession session=request.getSession(true);
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, "/WEB-INF/jsp/registration.jsp");
		request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
		
	}
	

}
