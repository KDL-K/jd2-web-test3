package by.htp.ts.service;

import java.util.List;

import by.htp.ts.bean.FinishedTest;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserProgress;


public interface TestService {
	int addTest(int userId, String testTitle, int testDuration) throws ServiceException;;
	boolean addQuestion(Question question) throws ServiceException;
	List<Test> receiveCreatedTests(int userId) throws ServiceException;
	List<Test> receiveAvailableTests() throws ServiceException;
	boolean saveTest(Test test) throws ServiceException;
	Test takeTest(int testId) throws ServiceException;
	int finishTest(Test test, FinishedTest finishedTest) throws ServiceException;
	List<UserProgress> receiveProgress(int userId) throws ServiceException;
	boolean deleteTest(int testId) throws ServiceException;


}
