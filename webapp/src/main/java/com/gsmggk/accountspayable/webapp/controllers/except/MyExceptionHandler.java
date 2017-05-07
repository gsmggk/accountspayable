package com.gsmggk.accountspayable.webapp.controllers.except;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gsmggk.accountspayable.dao4db.impl.exeption.MyDuplicateKeyException;
import com.gsmggk.accountspayable.dao4db.impl.exeption.MyNotFoundException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyAccessDeniedException;
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	 @ExceptionHandler(value = {MyDuplicateKeyException.class })
	    protected ResponseEntity<?> handleConflictREE(RuntimeException ex, WebRequest request) {
	      String bodyOfResponse = "{\"error\":\"Allready exist.\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.CONFLICT, request);
}
	 @ExceptionHandler(value = {MyNotFoundException.class })
	    protected ResponseEntity<?> handleConflictMNFE(RuntimeException ex, WebRequest request) {
		 String message= ex.getMessage();
		 String bodyOfResponse = "{\"error\":\""+message+".\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.NOT_FOUND, request);
}
	 
	 @ExceptionHandler(value = {IllegalArgumentException.class })
	    protected ResponseEntity<?> handleConflictIAE(RuntimeException ex, WebRequest request) {
	      String bodyOfResponse = "{\"error\":\"Duplicate key Exception.\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.CONFLICT, request);
}
	 
	 
	 @ExceptionHandler(value = {MyAccessDeniedException.class })
	    protected ResponseEntity<?> handleConflictMADE(RuntimeException ex, WebRequest request) {
	                String message= ex.getMessage();
	      String bodyOfResponse = "{\"error\":\""+message+".\"}";
	   
	        return handleExceptionInternal(ex, bodyOfResponse,  new HttpHeaders(), HttpStatus.FORBIDDEN, request);
}
		 
	 
 

	 
}
