package by.htp.ts.dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = 3073482915463957775L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception e) {
		super(e);
	}
	

}
