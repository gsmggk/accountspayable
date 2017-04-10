package com.gsmggk.accountspayable.dao4db.except;

public class MyBadPasswordException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyBadPasswordException() {
		
	}

	public MyBadPasswordException(String s) {
		super(s);
		
	}

	public MyBadPasswordException(Throwable cause) {
		super(cause);
		
	}

	public MyBadPasswordException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
