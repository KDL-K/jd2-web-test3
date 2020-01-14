package by.htp.ts.dao;

import java.util.List;

import by.htp.ts.bean.*;

public interface TestDAO {
	int addTest(int userId, String testTitle, int testDuration)throws DAOException;
	boolean saveQuestion(Question question) throws DAOException;
	List<Test> receiveCreatedTests(int userId) throws DAOException;
	void deleteTest(int testId) throws DAOException;
	List<Test> receiveAvailableTests() throws DAOException;
	Test takeTest(int testId) throws DAOException;
	boolean saveTest(Test test) throws DAOException;
	int saveTest(FinishedTest finishedTest)throws DAOException;
}
