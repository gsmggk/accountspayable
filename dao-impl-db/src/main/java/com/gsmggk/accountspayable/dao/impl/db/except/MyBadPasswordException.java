package com.gsmggk.accountspayable.dao.impl.db.except;

public class MyBadPasswordException extends IllegalArgumentException {

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
