package com.gsmggk.accountspayable.services.impl.exceptions;

public class MyAccessDeniedException extends RuntimeException {

	public MyAccessDeniedException() {
		super();
	
	}

	
	public MyAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MyAccessDeniedException(String message) {
		super(message);
	
	}

	public MyAccessDeniedException(Throwable cause) {
		super(cause);
		
	}

}
