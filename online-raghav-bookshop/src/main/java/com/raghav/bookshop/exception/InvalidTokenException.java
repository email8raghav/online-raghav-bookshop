package com.raghav.bookshop.exception;

public class InvalidTokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4658187413451004084L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
