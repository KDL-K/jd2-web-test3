package by.htp.ts.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.Test;
import by.htp.ts.bean.User;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;

public class TestListCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		try {
			List<Test> availableTestsList;
			ServiceFactory service = ServiceFactory.getInstance();
			availableTestsList = service.getTestS().receiveAvailableTests();
			session.setAttribute(CommandImplParameter.AVAILABLE_TESTS, availableTestsList);
			response.sendRedirect("Controller?command=go_to_test_list_page");
		}catch(ServiceException e) {
			response.sendRedirect("Controller?command=go_to_error_page&error_message=Connection has failed");
		}
		
	}

}
