package by.htp.ts.bean;

import java.io.Serializable;

public class UserProgress implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int progress;
	private String testName;
	
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + progress;
		result = prime * result + ((testName == null) ? 0 : testName.hashCode());
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
		UserProgress other = (UserProgress) obj;
		if (progress != other.progress)
			return false;
		if (testName == null) {
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName))
			return false;
		return true;
	}
	
	
	

}
