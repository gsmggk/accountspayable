package com.gsmggk.accountspayable.dao4db.impl.exeption;

public class MyNotFoundException extends IllegalArgumentException{

	

	public MyNotFoundException() {
		super();
		
	}

	public MyNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MyNotFoundException(String s) {
		super(s);
		
	}

	public MyNotFoundException(Throwable cause) {
		super(cause);
		
	}
}