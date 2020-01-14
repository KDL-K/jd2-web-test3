package by.htp.ts.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class ChangeLanguage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session=request.getSession(true);
	    String local=request.getParameter("lang");
	    session.setAttribute("local",local);
	    
	    String goToRequest = (String)session.getAttribute(CommandImplParameter.GOTO_REQUEST);
	    
	    if (goToRequest == null) {
	    	return;
	    }
	    
	    if (goToRequest.endsWith(".jsp")) {
	    	System.out.println("from land "+goToRequest);
	    	RequestDispatcher dispatcher=request.getRequestDispatcher(goToRequest);
			dispatcher.forward(request, response);
	    	
	    }else {
	    	response.sendRedirect(goToRequest);
	    }
	    
	}

}
