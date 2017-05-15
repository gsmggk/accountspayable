package com.gsmggk.accountspayable.webapp.controllers.except;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gsmggk.accountspayable.dao4api.exception.MyDuplicateKeyException;
import com.gsmggk.accountspayable.dao4api.exception.MyNotFoundException;
import com.gsmggk.accountspayable.dao4api.exception.MyUnsupportedOperationException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyAccessDeniedException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { MyDuplicateKeyException.class })
	protected ResponseEntity<?> handleConflictREE(RuntimeException ex, WebRequest request) {

		return handleExceptionInternal(ex, getMessage("Allready exist."), new HttpHeaders(), HttpStatus.CONFLICT,
				request);
	}

	@ExceptionHandler(value = { MyUnsupportedOperationException.class })
	protected ResponseEntity<?> handleConflictMUOE(RuntimeException ex, WebRequest request) {

		return handleExceptionInternal(ex, getMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED,
				request);
	}

	@ExceptionHandler(value = { MyNotFoundException.class })
	protected ResponseEntity<?> handleConflictMNFE(RuntimeException ex, WebRequest request) {
		

		return handleExceptionInternal(ex,  getMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * format json error message
	 * @param message
	 * @return
	 */
	private String getMessage(String message) {
		String bodyOfResponse = "{\"error\":\"" + message + ".\"}";
		return bodyOfResponse;
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<?> handleConflictIAE(RuntimeException ex, WebRequest request) {
	

		return handleExceptionInternal(ex,  getMessage("Duplicate key Exception."), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { MyAccessDeniedException.class })
	protected ResponseEntity<?> handleConflictMADE(RuntimeException ex, WebRequest request) {
	

		return handleExceptionInternal(ex,  getMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

}
