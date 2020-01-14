package by.htp.ts.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ts.command.CommandProvider;
import by.htp.ts.conpool.ConnectionPool;



public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PARAMETER_NAME="command";
    private CommandProvider provider=new CommandProvider();  
    private static ConnectionPool connectionPool;

    public Controller() {
        super();

    }
    
    @Override
	public void init() throws ServletException{
    	connectionPool=new ConnectionPool();
    	super.init();
    }
    
    public static ConnectionPool getConnectionPool() {
    	return connectionPool;
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		String commandStr=request.getParameter(PARAMETER_NAME);
		provider.getCommand(commandStr).execute(request, response);
	}
	
	@Override
	public void destroy(){
    	connectionPool.closeConnectionPool();
    	super.destroy();
    }

}
