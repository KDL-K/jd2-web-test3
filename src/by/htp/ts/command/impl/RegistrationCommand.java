package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserPlusLogPass;
import by.htp.ts.command.Command;
import by.htp.ts.service.ClientService;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;

public class RegistrationCommand implements Command {
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		final String SUCCESS="You have been registered. Please log in";
		final String EXIST_LOGIN="Entered login is exist. Please try again.";
		
		String login = request.getParameter(CommandImplParameter.LOGIN);
		String password = request.getParameter(CommandImplParameter.PASSWORD);
		
		User user = new User();
		user.setEmail(request.getParameter(CommandImplParameter.EMAIL));
		user.setName(request.getParameter(CommandImplParameter.NAME));
		user.setSurname(request.getParameter(CommandImplParameter.SURNAME));
		user.setRole(request.getParameter(CommandImplParameter.ROLE));
		user.setSex(request.getParameter(CommandImplParameter.SEX));
		user.setAge(Integer.parseInt(request.getParameter(CommandImplParameter.AGE)));
		
		UserPlusLogPass userPLP=new UserPlusLogPass();
		userPLP.setUser(user);
		userPLP.setLogin(login);
		userPLP.setPassword(password);
		
		HttpSession session=request.getSession(true);
		
		String errorMessage = (String)session.getAttribute(CommandImplParameter.ERROR_MESSAGE);
		if(errorMessage != null) {
			session.removeAttribute(CommandImplParameter.MESSAGE);
		}
		
		String goToPage;
		boolean isRegistered;
		try {
			ServiceFactory service=ServiceFactory.getInstance();
			ClientService clientS=service.getClientS();
			isRegistered=clientS.register(userPLP);
			if(isRegistered) {
				goToPage="/WEB-INF/jsp/authorization.jsp";
				session.setAttribute(CommandImplParameter.MESSAGE, SUCCESS);
			}else {
				goToPage="/WEB-INF/jsp/registration.jsp";
				session.setAttribute(CommandImplParameter.MESSAGE, EXIST_LOGIN);
			}
		}catch(ServiceException e) {
			session.setAttribute(CommandImplParameter.ERROR_MESSAGE, CommandImplParameter.DB_CONNECTION_HAS_FAILED);
			goToPage="/WEB-INF/jsp/error.jsp";
		}
		
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, goToPage);
		response.sendRedirect(CommandImplParameter.GO_TO_SOME_PAGE);
		
	}

}
