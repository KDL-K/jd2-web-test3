package by.htp.ts.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.FinishedTest;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserProgress;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.DAOFactory;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.dao.UserDAO;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.TestService;

public class TestServiceImpl implements TestService{
	
	@Override
	public int addTest(int userId, String testTitle, int testDuration) throws ServiceException{
		if(testTitle==null || testTitle.isEmpty()) {
			throw new ServiceException("Test title is empty.");
		}
		if(testDuration <= 0) {
			throw new ServiceException("Incorretc Duration.");
		}
		
		int testId;
		
		try {
			DAOFactory daoFactory=DAOFactory.getInstance();
			TestDAO testDao=daoFactory.getTestDao();
			testId=testDao.addTest(userId, testTitle, testDuration);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}	
		
		return testId;
	}

	@Override
	public boolean addQuestion(Question question) throws ServiceException{
		if (question == null) {
			return false;
		}
		
		boolean isCreateTest = false;
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			isCreateTest = testDAO.saveQuestion(question);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		
		if(isCreateTest) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Test> receiveCreatedTests(int userId) throws ServiceException {
		List<Test> testList=new ArrayList<Test>();
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			testList = testDAO.receiveCreatedTests(userId);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		
		return testList;
	}
	
	@Override
	public List<Test> receiveAvailableTests() throws ServiceException {
		List<Test> testList=new ArrayList<Test>();
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			testList = testDAO.receiveAvailableTests();
		}catch (DAOException e) {
			throw new ServiceException();
		}
		
		return testList;
	}

	@Override
	public boolean deleteTest(int testId) throws ServiceException{
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			testDAO.deleteTest(testId);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public Test takeTest(int testId) throws ServiceException {
		Test test=new Test();
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			test = testDAO.takeTest(testId);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		return test;
	}

	@Override
	public boolean saveTest(Test test) throws ServiceException {
		if (test == null) {
			return false;
		}
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			testDAO.saveTest(test);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		
		return true;
	}

	@Override
	public int finishTest(Test test, FinishedTest finishedTest) throws ServiceException {
		int result = 0;
		
		if (finishedTest != null) {
			int userRightAnswerCount = 0;
			List<Question> questionList = test.getQuestionList();
			for (Question question : questionList) {
				List<Integer> questionRightAnswerId = new ArrayList<Integer>();
				List<Integer> userRightAnswerId = finishedTest.getQuestionAnswer().get(question.getId());
				
				for (Answer answer : question.getAnswerList()) {
					if(answer.getIsRightAnswer()) {
						questionRightAnswerId.add(answer.getId());
					}
				}
				
				if(questionRightAnswerId.size() != userRightAnswerId.size()) {
					continue;
				}else {
					int count = 0;
					for (int id : userRightAnswerId) {
						if (questionRightAnswerId.contains(id)) {
							count++;
						}
					}
					if(count == questionRightAnswerId.size()) {
						userRightAnswerCount++;
					}
				}
			}
			
			result = (int)(((double)userRightAnswerCount / questionList.size())*100);
			finishedTest.setResult(result);
		}
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoFactory.getTestDao();
			testDAO.saveTest(finishedTest);
		}catch (DAOException e) {
			throw new ServiceException();
		}
		return result;
	}

	@Override
	public List<UserProgress> receiveProgress(int userId) throws ServiceException {
		List<UserProgress> userProgress = new ArrayList<UserProgress>();
		for(int i = 0; i<3; i++) {
			UserProgress progress = new UserProgress();
			progress.setProgress(70+i*2);
			progress.setTestName("Test ¹ "+((i+1)*3));
			userProgress.add(progress);
		}
		return userProgress;
	}

	

}
