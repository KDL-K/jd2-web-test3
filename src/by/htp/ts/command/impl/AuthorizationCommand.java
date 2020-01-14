package by.htp.ts.command.impl;

import java.io.IOException;
import by.htp.ts.service.ClientService;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.command.Command;

public class AuthorizationCommand implements Command {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String ERROR_LOG_PASS="Incorrect login or password";
		
		String login=request.getParameter(CommandImplParameter.LOGIN);
		String password=request.getParameter(CommandImplParameter.PASSWORD);
		if(login=="" || password=="") {
			request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp").forward(request,response);
			return;
		}
		
		HttpSession session=request.getSession(true);
		
		String goToPage;
		try {
			ServiceFactory service=ServiceFactory.getInstance();
			ClientService clientS=service.getClientS();
			User user=clientS.signIn(login, password);
			if(user!=null) {
				goToPage="/WEB-INF/jsp/main.jsp";
				session.setAttribute(CommandImplParameter.USER, user);
				
			}else {
				goToPage="/WEB-INF/jsp/authorization.jsp";
				session.setAttribute(CommandImplParameter.MESSAGE, ERROR_LOG_PASS);
			}
		}catch(ServiceException e) {
			//log
			session.setAttribute(CommandImplParameter.ERROR_MESSAGE, CommandImplParameter.DB_CONNECTION_HAS_FAILED);
			goToPage="/WEB-INF/jsp/error.jsp";
		}
		System.out.println("gotoRequest from authorization " +goToPage);
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, goToPage);
		response.sendRedirect(CommandImplParameter.GO_TO_SOME_PAGE);
		
	}

}
