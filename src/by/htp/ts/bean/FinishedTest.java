package by.htp.ts.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinishedTest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int testId;
	private Map<Integer, List<Integer>> questionAnswer = new HashMap<Integer,List<Integer>>();
	private int result;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	public Map<Integer, List<Integer>> getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(Map<Integer, List<Integer>> questionAnswer) {
		this.questionAnswer.putAll(questionAnswer);
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionAnswer == null) ? 0 : questionAnswer.hashCode());
		result = prime * result + this.result;
		result = prime * result + testId;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinishedTest other = (FinishedTest) obj;
		if (questionAnswer == null) {
			if (other.questionAnswer != null)
				return false;
		} else if (!questionAnswer.equals(other.questionAnswer))
			return false;
		if (result != other.result)
			return false;
		if (testId != other.testId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	

}
