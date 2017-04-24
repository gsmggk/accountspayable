package com.gsmggk.accountspayable.webapp.validate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public  class ValidationErrorRestonse {
	
	 public ResponseEntity<?> getValidationErrorRestonse(Errors e) {
		 ErrorModel error=new ErrorModel();
         error.setObject(e.getObjectName());
         error.setField(e.getFieldError().getField());
         error.setMessage(e.getFieldError().getDefaultMessage());
          return new ResponseEntity<ErrorModel>(error, HttpStatus.BAD_REQUEST);
			
 
}}
