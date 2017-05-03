package com.gsmggk.accountspayable.webapp.validate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public  class ParameterErrorResponse {
public static ResponseEntity<?> getNotFoundResponse(String message) {
	String bodyOfResponse = "{\"error\":\""+message+"\"}";
	return new ResponseEntity<String>(bodyOfResponse,HttpStatus.NOT_FOUND);
}
}
