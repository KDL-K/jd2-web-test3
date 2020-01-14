package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;

public class SaveTestNameAndDuration implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String MESSAGE_DURATION = "Incorrect type of Duration";
		final String TEST_TITLE = "test_title";
		final String TEST_DURATION = "test_duration";
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		String goToPage;
		
		try {
		   int testDuration=Integer.parseInt(request.getParameter(TEST_DURATION));
		   String testTitle = request.getParameter(TEST_TITLE);
		   int userId = ((User)session.getAttribute(CommandImplParameter.USER)).getId();
		   
		   ServiceFactory service=ServiceFactory.getInstance();
		   TestService tService=service.getTestS();	   
		   
		   int testId = tService.addTest(userId, testTitle, testDuration);	
		   
		   goToPage = "/WEB-INF/jsp/questionadd.jsp";
		   session.setAttribute(CommandImplParameter.TEST_ID, testId);
		  
		}catch(NumberFormatException e) {
			goToPage = "/WEB-INF/jsp/testcreation.jsp";
			session.setAttribute(CommandImplParameter.MESSAGE, MESSAGE_DURATION);
		}catch(ServiceException e) {
			session.setAttribute(CommandImplParameter.ERROR_MESSAGE, CommandImplParameter.DB_CONNECTION_HAS_FAILED);
			goToPage="/WEB-INF/jsp/error.jsp";
		}
		
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, goToPage);
		response.sendRedirect(CommandImplParameter.GO_TO_SOME_PAGE);
		
	}

}
