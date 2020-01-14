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

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceFactory;
import by.htp.ts.service.TestService;
import by.htp.ts.bean.Test;

public class TestEditionCommand implements Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String Q = "q";
		final String A = "a";
		final String R = "r";
		final String TEST_TITLE = "test_title";
		final String TEST_DURATION = "test_duration";
		
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		Test test = (Test)session.getAttribute(CommandImplParameter.TEST);
		Map<String,String[]> requestParameterMap = request.getParameterMap();
		Set<String> requestParameterNames = requestParameterMap.keySet();
		String[] parameterNames = requestParameterNames.toArray(new String[requestParameterNames.size()]);
		Map<Integer, String> questionMap = new HashMap<Integer, String>();
		Map<Integer, String> answerNameMap = new HashMap<Integer, String>();
		
		try {
			
			String[] rightAnswers;
			for (String param : parameterNames) {
				String[] splitParam = param.split(" ");
				String[] paramValue;
				if (splitParam[0].equals(Q)) {
					paramValue = requestParameterMap.get(param);
					questionMap.put(Integer.parseInt(splitParam[1]), paramValue[0]);
				}
				if(splitParam[0].equals(A)) {
					paramValue = requestParameterMap.get(param);
					answerNameMap.put(Integer.parseInt(splitParam[1]), paramValue[0]);
				}
			}
			rightAnswers = requestParameterMap.get(R);
			
			List<Question> questionList = test.getQuestionList();
			int questionListSize = questionList.size();
			for (int i = 0; i < questionListSize; i++) {
				List<Answer> answerList = questionList.get(i).getAnswerList();
				int answerListSize = answerList.size();
				for (int a = 0; a < answerListSize; a++) {
					Answer answer = ((ArrayList<Answer>)answerList).get(a);
					if (answerNameMap.containsKey(answer.getId())) {
						answer.setName(answerNameMap.get(answer.getId()));
						answer.setIsRightAnswer(false);
						for (String rightAnswer : rightAnswers) {
							if (Integer.parseInt(rightAnswer) == answer.getId()) {
								answer.setIsRightAnswer(true);
							}	
						}
					}
				}
			}
			
			test.setName(request.getParameter(TEST_TITLE));
			test.setTestDuration(Integer.parseInt(request.getParameter(TEST_DURATION)));
			
			ServiceFactory service=ServiceFactory.getInstance();
			TestService tService=service.getTestS();
			boolean isSaved = tService.saveTest(test);
			if (isSaved) {
				response.sendRedirect("Controller?command=go_to_main_page&message=Test has saved successfully.");
			}else {
				response.sendRedirect("Controller?command=go_to_main_page&message=Test has NOT. Please try again.");
			}
			
		}catch(NumberFormatException | ServiceException e) {
			response.sendRedirect("Controller?command=go_to_error_page&error_message=Connection has failed");
		}
			
	}
}
