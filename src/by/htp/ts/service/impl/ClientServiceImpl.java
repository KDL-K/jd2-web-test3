package by.htp.ts.service.impl;

import by.htp.ts.service.ClientService;
import by.htp.ts.service.ServiceException;
import by.htp.ts.bean.UserPlusLogPass;
import by.htp.ts.bean.User;
import by.htp.ts.dao.*;

public class ClientServiceImpl implements ClientService{

	@Override
	public User signIn(String login, String password) throws ServiceException {
		checkNull(login, password);
		User user;
		try {
			DAOFactory daoFactory=DAOFactory.getInstance();
			UserDAO userDao=daoFactory.getUserDao();
			user=userDao.authorization(login, password);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public boolean register(UserPlusLogPass userPLP) throws ServiceException{
		checkNull(userPLP.getLogin(), userPLP.getPassword());
		boolean isRegistered;
		
		try {
			DAOFactory daoFactory=DAOFactory.getInstance();
			UserDAO userDao=daoFactory.getUserDao();
			isRegistered=userDao.registration(userPLP);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
		
		if(isRegistered) {
			return true;
		}else {
			return false;
		}
	}
	
	private void checkNull(String login, String password) throws ServiceException {
		if(login==null || login.isEmpty()) {
			throw new ServiceException("Login isn't specified.");
		}
		if(password==null || password.isEmpty()) {
			throw new ServiceException("Password isn't specified.");
		}
	}
	
	
	

}
