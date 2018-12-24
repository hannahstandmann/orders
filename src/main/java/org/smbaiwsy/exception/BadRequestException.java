package org.smbaiwsy.exception;


import lombok.Getter;

/**
 * Used to handle the bad request exception.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
public class BadRequestException extends Exception {

	private static final long serialVersionUID = -6507934884227938446L;
	
	@Getter
	private String errorCode;

	/**
	 * Instantiates a new bad request exception.
	 *
	 * @param message the message
	 */
	public BadRequestException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
