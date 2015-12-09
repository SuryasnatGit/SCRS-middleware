/**
 * 
 */
package edu.umn.csci5801.exceptions;

/**Exception thrown when a user attempts to perform a class operation that is not within the timeframe.
 * @author wclee
 *
 */
public class NotWithinTimeFrameException extends Exception {

	public NotWithinTimeFrameException() {
	}

	/**
	 * @param message
	 */
	public NotWithinTimeFrameException(String message) {
		super(message);
	}
}
