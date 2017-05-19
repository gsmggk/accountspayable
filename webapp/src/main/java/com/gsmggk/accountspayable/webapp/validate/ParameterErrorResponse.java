package com.gsmggk.accountspayable.webapp.validate;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ParameterErrorResponse {
	public static ResponseEntity<?> getNotFoundResponse(String message) {
		String bodyOfResponse = "{\"error\":\"" + message + "\"}";
		return new ResponseEntity<String>(bodyOfResponse, HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> get404Response() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
  
	public static boolean allowLayer(String prefix, String[] strings) {

        if (Arrays.asList(strings).contains(prefix)) {
            return true;
        } else {
            return false;
        }
    }


}
