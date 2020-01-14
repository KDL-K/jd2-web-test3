package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;

public class DeleteTestCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String CONNECTION_HAS_FAILED = "Connection has failed. Please log in";
		
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		try {
			int testId = Integer.parseInt(request.getParameter(CommandImplParameter.TEST_ID));
			ServiceFactory service = ServiceFactory.getInstance();
			TestService tService = service.getTestS();
			tService.deleteTest(testId);
			
			session.setAttribute(CommandImplParameter.GOTO_REQUEST, "Controller?command=created_tests");
			response.sendRedirect("Controller?command=created_tests");
		}catch(NumberFormatException | ServiceException e) {
			session.setAttribute(CommandImplParameter.MESSAGE, CONNECTION_HAS_FAILED);
			session.setAttribute(CommandImplParameter.GOTO_REQUEST, "/WEB-INF/jsp/authorization.jsp");
			response.sendRedirect(CommandImplParameter.GO_TO_SOME_PAGE);
		}
		
	}

}
