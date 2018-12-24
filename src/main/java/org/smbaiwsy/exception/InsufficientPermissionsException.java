package org.smbaiwsy.exception;


import lombok.Getter;

/**
 * Used to handle the insufficient permission exception.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
public class InsufficientPermissionsException extends Exception {

	private static final long serialVersionUID = -6507934884227938446L;
	
	@Getter
	private String errorCode;

	/**
	 * Instantiates a new insufficient permission exception.
	 *
	 * @param message the message
	 */
	public InsufficientPermissionsException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
