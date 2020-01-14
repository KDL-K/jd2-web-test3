package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.Answer;
import by.htp.ts.bean.FinishedTest;
import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.controller.Controller;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.TestDAO;

public class TestDAOImpl implements TestDAO{
	private final String ID = "id";
	private final String TEST_NAME = "test_name";
	private final String TEST_TIME = "test_time";
	private final String QUESTION_TITLE = "question_title";
	private final String ANSWER_TITLE = "answer_title";
	private final String CORRECT_ANSWER = "correct_answer";
	
	@Override
	public int addTest(int userId, String testTitle, int testDuration) throws DAOException {
		Connection connection=Controller.getConnectionPool().takeConnection();
		final String PST_IN_TEST = "INSERT INTO test(test_name,test_time,user_id) VALUE(?,?,?)";
		final String PST_GET_TEST_ID = "SELECT id FROM test WHERE id=LAST_INSERT_ID()";

		PreparedStatement pstInTest = null;
		PreparedStatement pstGetTestId = null;
		ResultSet rsGetTestId = null;
		int testId;

		ReentrantLock dbLock = new ReentrantLock();
		dbLock.lock();
		try {
			connection.setAutoCommit(false);
			
			pstInTest = connection.prepareStatement(PST_IN_TEST);
			pstInTest.setString(1, testTitle);
			pstInTest.setInt(2, testDuration);
			pstInTest.setInt(3, userId);
			pstInTest.executeUpdate();
			
			pstGetTestId = connection.prepareStatement(PST_GET_TEST_ID);
			rsGetTestId = pstGetTestId.executeQuery();
			
			rsGetTestId.next();
			testId = rsGetTestId.getInt(ID);
			
			connection.commit();
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {
			try {
				connection.rollback();
			} catch (SQLException e) {
				//log
				throw new DAOException(e);
			}
			
			
			if(pstInTest!=null) {
				try {
					pstInTest.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(rsGetTestId!=null) {
				try {
					rsGetTestId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetTestId!=null) {
				try {
					pstGetTestId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		dbLock.unlock();
		return testId;
	}

	@Override
	public boolean saveQuestion(Question question) throws DAOException{
		Connection connection=Controller.getConnectionPool().takeConnection();
		final String PST_IN_QUESTION = "INSERT INTO question(question_title,test_id) VALUE(?,?)";
		final String PST_GET_QUESTION_ID = "SELECT id FROM question WHERE id=LAST_INSERT_ID()";
		final String PST_IN_ANSWERS = "INSERT INTO answers(answer_title,correct_answer,question_id) VALUE(?,?,?)";

		int answersCount = question.getAnswerList().size();
		
		PreparedStatement pstInQuestion = null;
		PreparedStatement pstGetQuestionId = null;
		PreparedStatement[] pstInAnswers = new PreparedStatement[answersCount];
		ResultSet rsGetQuestionId = null;
		int questionId;

		ReentrantLock dbLock = new ReentrantLock();
		dbLock.lock();
		
		try {
			connection.setAutoCommit(false);
			
			pstInQuestion = connection.prepareStatement(PST_IN_QUESTION);
			pstInQuestion.setString(1, question.getName());
			pstInQuestion.setInt(2, question.getTestId());
			
			pstInQuestion.executeUpdate();
			
			pstGetQuestionId = connection.prepareStatement(PST_GET_QUESTION_ID);
			rsGetQuestionId = pstGetQuestionId.executeQuery();
			
			rsGetQuestionId.next();
			questionId = rsGetQuestionId.getInt(ID);
			
			for(int i=0; i<answersCount; i++) {
				pstInAnswers[i] = connection.prepareStatement(PST_IN_ANSWERS);
				pstInAnswers[i].setString(1, question.getAnswerList().get(i).getName());
				pstInAnswers[i].setInt(2, question.getAnswerList().get(i).getIsRightAnswer()?1:0);
				pstInAnswers[i].setInt(3, questionId);
				pstInAnswers[i].executeUpdate();
			}
			
			connection.commit();
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {
			try {
				connection.rollback();
			} catch (SQLException e) {
				//log
				throw new DAOException(e);
			}
			
			if(pstInQuestion!=null) {
				try {
					pstInQuestion.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(rsGetQuestionId!=null) {
				try {
					rsGetQuestionId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetQuestionId!=null) {
				try {
					pstGetQuestionId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			for(int i=0; i<answersCount; i++) {
				if(pstInAnswers[i]!=null) {
					try {
						pstInAnswers[i].close();
					}catch(SQLException e) {
						//log
						throw new DAOException(e);
					}
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		dbLock.unlock();
		return true;
	}
	
	@Override
	public List<Test> receiveCreatedTests(int userId) throws DAOException {
		final String PST_GET_CREATED_TESTS = "SELECT id, test_name FROM test WHERE user_id=?";
		
		Connection connection=Controller.getConnectionPool().takeConnection();

		PreparedStatement pstGetCreatedTests = null;
		ResultSet rsGetCreatedTests = null;
		List<Test> testList=new ArrayList<Test>();

		try {
			
			pstGetCreatedTests = connection.prepareStatement(PST_GET_CREATED_TESTS);
			pstGetCreatedTests.setInt(1, userId);
			rsGetCreatedTests = pstGetCreatedTests.executeQuery();
			
			while(rsGetCreatedTests.next()) {
				Test test = new Test();
				test.setId(rsGetCreatedTests.getInt(ID));
				test.setName(rsGetCreatedTests.getString(TEST_NAME));
				testList.add(test);
			}
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {	
			
			if(rsGetCreatedTests!=null) {
				try {
					rsGetCreatedTests.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetCreatedTests!=null) {
				try {
					pstGetCreatedTests.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		
		return testList;
	}

	@Override
	public void deleteTest(int testId) throws DAOException{
		Connection connection=Controller.getConnectionPool().takeConnection();
		final String PST_GET_QUESTION_ID = "SELECT id FROM question WHERE test_id=?";
		final String PST_DELETE_ANSWERS = "DELETE FROM answers WHERE question_id=?";
		final String PST_DELETE_QUESTIONS = "DELETE FROM question WHERE test_id=?";
		final String PST_DELETE_TEST = "DELETE FROM test WHERE id=?";

		PreparedStatement pstGetQuestionId = null;
		PreparedStatement[] pstDeleteAnswers = null;
		PreparedStatement pstDeleteQuestions = null;
		PreparedStatement pstDeleteTest = null;
		ResultSet rsGetQuestionId = null;
		
		int questionCount = 0;
		List<Integer> questionIdList = new ArrayList<Integer>();

		ReentrantLock dbLock = new ReentrantLock();
		dbLock.lock();
		try {
			connection.setAutoCommit(false);
			
			pstGetQuestionId = connection.prepareStatement(PST_GET_QUESTION_ID);
			pstGetQuestionId.setInt(1, testId);
			rsGetQuestionId = pstGetQuestionId.executeQuery();
			
			while(rsGetQuestionId.next()) {
				questionIdList.add(rsGetQuestionId.getInt(ID));
			}
			
			questionCount = questionIdList.size();
			pstDeleteAnswers = new PreparedStatement[questionCount];
			
			for (int i=0; i<questionCount; i++) {
				pstDeleteAnswers[i] = connection.prepareStatement(PST_DELETE_ANSWERS);
				pstDeleteAnswers[i].setInt(1, questionIdList.get(i));
				pstDeleteAnswers[i].executeUpdate();
			}
			
			pstDeleteQuestions = connection.prepareStatement(PST_DELETE_QUESTIONS);
			pstDeleteQuestions.setInt(1, testId);
			pstDeleteQuestions.executeUpdate();
			
			pstDeleteTest = connection.prepareStatement(PST_DELETE_TEST);
			pstDeleteTest.setInt(1, testId);
			pstDeleteTest.executeUpdate();
			
			connection.commit();
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {
			try {
				connection.rollback();
			} catch (SQLException e) {
				//log
				throw new DAOException(e);
			}
			
			if(rsGetQuestionId!=null) {
				try {
					rsGetQuestionId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetQuestionId!=null) {
				try {
					pstGetQuestionId.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			for (int i=0; i<questionCount; i++) {
				if(pstDeleteAnswers[i]!=null) {
					try {
						pstDeleteAnswers[i].close();
					}catch(SQLException e) {
						//log
						throw new DAOException(e);
					}
				}
			}
			
			if(pstDeleteQuestions!=null) {
				try {
					pstDeleteQuestions.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(pstDeleteTest!=null) {
				try {
					pstDeleteTest.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		dbLock.unlock();	
	}
	
	@Override
	public List<Test> receiveAvailableTests() throws DAOException {
		final String PST_GET_AVAILABLE_TESTS = "SELECT id, test_name FROM test";
		
		Connection connection=Controller.getConnectionPool().takeConnection();

		PreparedStatement pstGetAvailableTests = null;
		ResultSet rsGetAvailableTests = null;
		List<Test> testList=new ArrayList<Test>();

		try {
			
			pstGetAvailableTests = connection.prepareStatement(PST_GET_AVAILABLE_TESTS);
			rsGetAvailableTests = pstGetAvailableTests.executeQuery();
			
			while(rsGetAvailableTests.next()) {
				Test test = new Test();
				test.setId(rsGetAvailableTests.getInt(ID));
				test.setName(rsGetAvailableTests.getString(TEST_NAME));
				testList.add(test);
			}
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {	
			
			if(rsGetAvailableTests!=null) {
				try {
					rsGetAvailableTests.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetAvailableTests!=null) {
				try {
					pstGetAvailableTests.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		
		return testList;
	}
	
	@Override
	public Test takeTest(int testId) throws DAOException {
		
        final String PST_GET_TEST = "SELECT* FROM test WHERE id=?";
        final String PST_GET_QUESTIONS = "SELECT* FROM question WHERE test_id=?";
        final String PST_GET_ANSWERS = "SELECT* FROM answers WHERE question_id=?";
        
        Test test = new Test();
        test.setId(testId);
        
        List<Question> questionList=new ArrayList<Question>();
        
		Connection connection=Controller.getConnectionPool().takeConnection();

		PreparedStatement pstGetTest = null;
		PreparedStatement pstGetQuestions = null;
		PreparedStatement[] pstGetAnswers = null;
		ResultSet rsGetTest = null;
		ResultSet rsGetQuestions = null;
		ResultSet[] rsGetAnswers = null;
		
		int questionCount=0;

		try {
			
			pstGetTest = connection.prepareStatement(PST_GET_TEST);
			pstGetTest.setInt(1, testId);
			rsGetTest = pstGetTest.executeQuery();
			
			while(rsGetTest.next()) {
				test.setName(rsGetTest.getString(TEST_NAME));
				test.setTestDuration(rsGetTest.getInt(TEST_TIME));
			}
			
			pstGetQuestions = connection.prepareStatement(PST_GET_QUESTIONS);
			pstGetQuestions.setInt(1, testId);
			rsGetQuestions = pstGetQuestions.executeQuery();
			
			while(rsGetQuestions.next()) {
				Question question = new Question();
				
				question.setId(rsGetQuestions.getInt(ID));
				question.setName(rsGetQuestions.getString(QUESTION_TITLE));
				question.setTestId(testId);
				
				questionList.add(question);
				
			}
			
			
			questionCount = questionList.size();
			pstGetAnswers = new PreparedStatement[questionCount];
			rsGetAnswers = new ResultSet[questionCount];
			for (int i=0; i<questionCount; i++) {
				Question question = questionList.get(i);
				int questionId = question.getId();
				
				pstGetAnswers[i] = connection.prepareStatement(PST_GET_ANSWERS);
				pstGetAnswers[i].setInt(1, questionId);
				rsGetAnswers[i] = pstGetAnswers[i].executeQuery();
				
				List<Answer> answerList=new ArrayList<Answer>();
				while(rsGetAnswers[i].next()) {
					Answer answer = new Answer();
					
					answer.setId(rsGetAnswers[i].getInt(ID));
					answer.setName(rsGetAnswers[i].getString(ANSWER_TITLE));
					answer.setIsRightAnswer(rsGetAnswers[i].getBoolean(CORRECT_ANSWER));
					answer.setQuestionId(questionId);
					
					answerList.add(answer);
					
				}
				question.setAnswerList(answerList);
				
			}

			test.setQuestionList(questionList);
			
			
			
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {	
			
			if(rsGetTest!=null) {
				try {
					rsGetTest.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			if(pstGetTest!=null) {
				try {
					pstGetTest.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			//----------
			
			//-----------
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		
		return test;
	}
	
	@Override
	public boolean saveTest(Test test) throws DAOException {
		
		final String PST_UPDATE_TEST = "UPDATE test SET test_name=?,test_time=? WHERE id=?";
		final String PST_UPDATE_QUESTION = "UPDATE question SET question_title=? WHERE id=?";
		final String PST_UPDATE_ANSWERS = "UPDATE answers SET answer_title=?, correct_answer=? WHERE id=?";

		PreparedStatement pstUpdateTest = null;
		PreparedStatement[] pstUpdateQuestion = null;
		PreparedStatement[] pstUpdateAnswers = null;

        List<Question> questionList = test.getQuestionList();
        List<Answer> answerListNew = new ArrayList<Answer>();
        
        int questionCount;
        int answerCount;
        
        for (Question question: questionList) {
        	List<Answer> answerList = question.getAnswerList();
        	answerListNew.addAll(answerList);
        }
		
		Connection connection=Controller.getConnectionPool().takeConnection();

		ReentrantLock dbLock = new ReentrantLock();
		dbLock.lock();
		try {
			connection.setAutoCommit(false);
			
			pstUpdateTest = connection.prepareStatement(PST_UPDATE_TEST);
			pstUpdateTest.setString(1, test.getName());
			pstUpdateTest.setInt(2, test.getTestDuration());
			pstUpdateTest.setInt(3, test.getId());
			pstUpdateTest.executeUpdate();
			
			questionCount = questionList.size();
			pstUpdateQuestion = new PreparedStatement[questionCount];
			for (int i=0; i<questionCount; i++) {
				Question question = questionList.get(i);
				pstUpdateQuestion[i] = connection.prepareStatement(PST_UPDATE_QUESTION);
				pstUpdateQuestion[i].setString(1, question.getName());
				pstUpdateQuestion[i].setInt(2, question.getId());
				pstUpdateQuestion[i].executeUpdate();
			}
			
			answerCount = answerListNew.size();
			pstUpdateAnswers = new PreparedStatement[answerCount];
			for (int i=0; i<answerCount; i++) {
				Answer answer = answerListNew.get(i);
				pstUpdateAnswers[i] = connection.prepareStatement(PST_UPDATE_ANSWERS);
				pstUpdateAnswers[i].setString(1, answer.getName());
				pstUpdateAnswers[i].setInt(2, answer.getIsRightAnswer()?1:0);
				pstUpdateAnswers[i].setInt(3, answer.getId());
				pstUpdateAnswers[i].executeUpdate();
			}

			connection.commit();
			
		}catch(SQLException e) { 
			throw new DAOException(e);
		}finally {
			try {
				connection.rollback();
			} catch (SQLException e) {
				//log
				throw new DAOException(e);
			}
			
			
			if(pstUpdateTest!=null) {
				try {
					pstUpdateTest.close();
				}catch(SQLException e) {
					//log
					throw new DAOException(e);
				}
			}
			//---------
			
			//------------
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//log
				}
			}
		}
		dbLock.unlock();

		return true;
	}
	
	@Override
	public int saveTest(FinishedTest finishedTest) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}


	

	

	

	

	

	

}
