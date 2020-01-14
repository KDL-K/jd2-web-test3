package by.htp.ts.bean;

import java.io.Serializable;

public class Answer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean isRightAnswer;
	private int id;
	private int questionId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsRightAnswer() {
		return isRightAnswer;
	}
	public void setIsRightAnswer(boolean isRightAnswer) {
		this.isRightAnswer = isRightAnswer;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	

}
