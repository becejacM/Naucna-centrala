package ftn.uns.ac.rs.naucnaCentrala.exceptions;

public class NotValidParamsException extends RuntimeException {

	private static final long serialVersionUID = 2426679664339393079L;

	public NotValidParamsException() {
		super();
	}

	public NotValidParamsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotValidParamsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidParamsException(String message) {
		super(message);
	}

	public NotValidParamsException(Throwable cause) {
		super(cause);
	}

}
