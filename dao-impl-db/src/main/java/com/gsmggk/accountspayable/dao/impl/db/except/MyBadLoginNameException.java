package com.gsmggk.accountspayable.dao.impl.db.except;

public class MyBadLoginNameException extends IllegalArgumentException{

	public MyBadLoginNameException() {
		super();
		
	}

	public MyBadLoginNameException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MyBadLoginNameException(String s) {
		super(s);
		
	}

	public MyBadLoginNameException(Throwable cause) {
		super(cause);
		
	}

	
}
