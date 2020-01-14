package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.command.Command;

public class LogOutCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user;
		HttpSession session=request.getSession();
		if(session == null) {
			System.out.println("No session");
			response.sendRedirect("Controller?command=go_to_authorization_page");
			return;
		}
		
		user=(User)session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect("Controller?command=go_to_authorization_page");
		}else {
			session.removeAttribute("user");
			response.sendRedirect("Controller?command=go_to_authorization_page");
		}
	}

}
