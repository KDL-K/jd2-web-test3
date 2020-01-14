package by.htp.ts.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Question> questionList=new ArrayList<Question>();
	private int testDuration;
	private int id;
	
	public Test() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList.addAll(questionList);
	}
	
	public int getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(int testDuration) {
		this.testDuration = testDuration;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((questionList == null) ? 0 : questionList.hashCode());
		result = prime * result + testDuration;
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
		Test other = (Test) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (questionList == null) {
			if (other.questionList != null)
				return false;
		} else if (!questionList.equals(other.questionList))
			return false;
		if (testDuration != other.testDuration)
			return false;
		return true;
	}

	

	
	

}
