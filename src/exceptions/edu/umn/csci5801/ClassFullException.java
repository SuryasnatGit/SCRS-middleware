package edu.umn.csci5801;

/**
 * @author wclee
 *
 */
public class ClassFullException extends Exception {

	/**
	 * 
	 */
	public ClassFullException() {

	}

	/**
	 * @param message
	 */
	public ClassFullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ClassFullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ClassFullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
