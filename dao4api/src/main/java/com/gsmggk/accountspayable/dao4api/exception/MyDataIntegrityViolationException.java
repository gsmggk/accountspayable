package com.gsmggk.accountspayable.dao4api.exception;

import org.springframework.dao.DataIntegrityViolationException;


public class MyDataIntegrityViolationException extends DataIntegrityViolationException {

	public MyDataIntegrityViolationException(String msg, Throwable cause) {
		super(msg, cause);
		
	}

	public MyDataIntegrityViolationException(String msg) {
		super(msg);
		
	}

}
