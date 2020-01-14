package by.htp.ts.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Answer> answerList = new ArrayList<Answer>();
	private boolean isValide;
	private int id;
	private int testId;
	
	
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
	public List<Answer> getAnswerList() {
		return answerList;
	}
	
	public void setAnswerList(List<Answer> answerList) {
		this.answerList.addAll(answerList);
	}
	
	public boolean getIsValide() {
		return isValide;
	}
	public void setValide(boolean isValide) {
		this.isValide = isValide;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	
	
	
	
	

}
