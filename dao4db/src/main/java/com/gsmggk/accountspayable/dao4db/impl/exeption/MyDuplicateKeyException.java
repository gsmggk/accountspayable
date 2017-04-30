package com.gsmggk.accountspayable.dao4db.impl.exeption;

import org.springframework.dao.DuplicateKeyException;

public class MyDuplicateKeyException extends DuplicateKeyException {

	public MyDuplicateKeyException(String msg, Throwable cause) {
		super(msg, cause);
	
	}

	public MyDuplicateKeyException(String msg) {
		super(msg);
		}

}
