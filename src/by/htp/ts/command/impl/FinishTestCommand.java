package by.htp.ts.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;

import by.htp.ts.bean.FinishedTest;
import by.htp.ts.bean.Test;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;

public class FinishTestCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String Q = "q";
		final String RESULT = "result";
		final int SEC_IN_MIN = 60;
		final int ADD_TIME_IN_SEC = 5;

		HttpSession session=request.getSession(true);
		
		long finishTime = Time.sec(Time.now());
		long startTime = (Long)session.getAttribute(CommandImplParameter.START_TIME);
		long testTime = ((Test)session.getAttribute(CommandImplParameter.TEST)).getTestDuration()*SEC_IN_MIN;
		
		try {
			if ((finishTime - startTime) > (testTime + ADD_TIME_IN_SEC)) {
				ServiceFactory service=ServiceFactory.getInstance();
				TestService tService=service.getTestS();
				tService.finishTest(null, null);
			}else {
				Map<String,String[]> requestParameterMap = request.getParameterMap();
				Set<String> requestParameterNames = requestParameterMap.keySet();
				Map<Integer, List<Integer>> questionAnswerMap = new HashMap<Integer, List<Integer>>();
				
					for (String param : requestParameterNames) {
						String[] splitParam = param.split(" ");
						String[] paramValueArray;
						if (splitParam[0].equals(Q)) {
							paramValueArray = requestParameterMap.get(param);
							List<Integer> rightAnswerIdList = new ArrayList<Integer>();
							for(String paramValue : paramValueArray) {
								rightAnswerIdList.add(Integer.parseInt(paramValue));
							}
							questionAnswerMap.put(Integer.parseInt(splitParam[1]), rightAnswerIdList);
						}
					}
					
					FinishedTest finishedTest = new FinishedTest();
					finishedTest.setUserId(Integer.parseInt(request.getParameter(CommandImplParameter.USER_ID)));
					finishedTest.setTestId(Integer.parseInt(request.getParameter(CommandImplParameter.TEST_ID)));
					finishedTest.setQuestionAnswer(questionAnswerMap);
					
					Test test = (Test)session.getAttribute(CommandImplParameter.TEST);
					
					ServiceFactory service=ServiceFactory.getInstance();
					TestService tService=service.getTestS();
					int result = tService.finishTest(test, finishedTest);
					
					session.setAttribute(RESULT, result);
			
		    }
			response.sendRedirect("Controller?command=go_to_test_result_page");
		}catch(NumberFormatException | ServiceException e) {
			response.sendRedirect("Controller?command=go_to_error_page&error_message=Connection has failed");
		}		
	}
}
