package com.gsmggk.accountspayable.webapp.controllers.except;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gsmggk.accountspayable.dao4db.impl.exeption.MyDuplicateKeyException;
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	 @ExceptionHandler(value = {MyDuplicateKeyException.class })
	    protected ResponseEntity<?> handleConflictREE(RuntimeException ex, WebRequest request) {
	      String bodyOfResponse = "{\"error\":\"Duplicate key Exception.\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.CONFLICT, request);
}
	 
	 @ExceptionHandler(value = {IllegalArgumentException.class })
	    protected ResponseEntity<?> handleConflictIAE(RuntimeException ex, WebRequest request) {
	      String bodyOfResponse = "{\"error\":\"Duplicate key Exception.\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.CONFLICT, request);
}
 
/*	 @ExceptionHandler(value = {NullPointerException.class })
	    protected ResponseEntity<?> handleConflictNPE(RuntimeException ex, WebRequest request) {
	      String bodyOfResponse = "{\"error\":\"NullPointerException.\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.CONFLICT, request);
} */
	 
	 
}
