package by.htp.ts.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.Question;
import by.htp.ts.command.Command;

public class GoToMarkRightAnswersPage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String QUESTION_TITLE = "question_title";
		final String ANSWERS = "answers";
		
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("Controller?command=go_to_authorization_page&message=Please log in");
			return;
		}
		
		int testId=(int)session.getAttribute(CommandImplParameter.TEST_ID);
		String questionTitle=request.getParameter(QUESTION_TITLE);
        String[] answers=request.getParameterValues(ANSWERS);
        
        List<Answer> answerList = new ArrayList<Answer>();
        
        int answerId = 1;
        
        for(String answerStr:answers) {
        	if(answerStr.isBlank()) {
        		continue;
        	}else {
        		Answer answer = new Answer();
        		answer.setName(answerStr);
        		answer.setId(answerId++);
        		answerList.add(answer);
        	}
        }
        
        Question question=new Question();
        question.setName(questionTitle);
        question.setAnswerList(answerList);
        question.setTestId(testId);
		
		session.setAttribute(CommandImplParameter.QUESTION, question);
		session.setAttribute(CommandImplParameter.GOTO_REQUEST, "/WEB-INF/jsp/rightanswersmark.jsp");
		
		request.getRequestDispatcher("/WEB-INF/jsp/rightanswersmark.jsp").forward(request, response);
		
	}

}
