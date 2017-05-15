package com.gsmggk.accountspayable.dao4api.exception;

public class MyUnsupportedOperationException extends UnsupportedOperationException {
	  public MyUnsupportedOperationException() {
	        super("This method is not supported by XML DAO layer");
	    }
}
