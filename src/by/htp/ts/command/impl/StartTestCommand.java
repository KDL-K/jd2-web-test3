package by.htp.ts.command.impl;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;

import by.htp.ts.bean.Test;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;

public class StartTestCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		try {
			int testId = Integer.parseInt(request.getParameter(CommandImplParameter.TEST_ID));
			Test test;
			ServiceFactory service = ServiceFactory.getInstance();
			TestService tService = service.getTestS();
			test = tService.takeTest(testId);
			
			long startTime = Time.sec(Time.now());
			
			session.setAttribute(CommandImplParameter.START_TIME, startTime);
			session.setAttribute(CommandImplParameter.TEST, test);
			response.sendRedirect("Controller?command=go_to_start_test_page");
		}catch(NumberFormatException | ServiceException e) {
			response.sendRedirect("Controller?command=go_to_error_page&error_message=Connection has failed");
		}
	}


}
