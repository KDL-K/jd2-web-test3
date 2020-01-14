package by.htp.ts.service;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserPlusLogPass;

public interface ClientService {
	User signIn(String login, String password) throws ServiceException;
	boolean register(UserPlusLogPass userPLP)throws ServiceException;
}
