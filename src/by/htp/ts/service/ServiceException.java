package by.htp.ts.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 2770195289712924388L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}
}
