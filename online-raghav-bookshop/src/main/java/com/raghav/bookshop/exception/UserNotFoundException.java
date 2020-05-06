package com.raghav.bookshop.exception;

public class UserNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = -971708534130483012L;
	
	//private String message;
	
	public UserNotFoundException(String message){
		super(message);
	}

}
