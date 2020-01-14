package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.Test;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;

public class EditTestCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		String goToPage;
		try {
			int testId = Integer.parseInt(request.getParameter(CommandImplParameter.TEST_ID));
			Test test;
			ServiceFactory service = ServiceFactory.getInstance();
			TestService tService = service.getTestS();
			test = tService.takeTest(testId);
			
			session.setAttribute(CommandImplParameter.TEST, test);
			goToPage = "/WEB-INF/jsp/testedition.jsp";
			
		}catch(NumberFormatException | ServiceException e) {
			session.setAttribute(CommandImplParameter.ERROR_MESSAGE, CommandImplParameter.DB_CONNECTION_HAS_FAILED);
			goToPage = "/WEB-INF/jsp/error.jsp";
		}
		
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, goToPage);
		response.sendRedirect(CommandImplParameter.GO_TO_SOME_PAGE);
	}

}
