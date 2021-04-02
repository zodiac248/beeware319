package com.panpal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.hibernate.exception.DataException;
import org.hibernate.exception.ConstraintViolationException;

public class ResultController {

	public ResponseEntity<String> handleSuccess(String msg){
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}	

	public ResponseEntity<String> handleError(Exception e){
		Throwable cause = e.getCause();
		
		if (cause instanceof DataException){
			return ResponseEntity.status(HttpStatus.URI_TOO_LONG).body("Input is too long: ");	
		} else if (cause instanceof ConstraintViolationException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Variable already exists: ");
		} 
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
	}
}
