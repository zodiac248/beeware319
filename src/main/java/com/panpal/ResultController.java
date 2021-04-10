package com.panpal;

import com.panpal.Error.*;
import org.hibernate.exception.JDBCConnectionException;
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
		System.out.println(e.getMessage());
        e.printStackTrace();

		if (e instanceof InputTooLongException){
			return ResponseEntity.status(HttpStatus.URI_TOO_LONG).body("Input is too long: should not exceed 1024 characters");
		} else if (cause instanceof ConstraintViolationException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Variable already exists: ");
		} else if (cause instanceof JDBCConnectionException){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't connect to database");
		} else if (e instanceof ExceedRangeException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking range exceeded the 6 month allowed period");
		} else if (e instanceof BuildingNoLongerExistsException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} else if (e instanceof FloorNoLongerExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} else if (e instanceof DeskNoLongerExistsException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("desk does not exists");
		} else if (e instanceof BookingNotExistsException){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("booking does not exists");
		} else if (e instanceof BookingNotAvailableException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}  else if (e instanceof DuplicateFloorException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} else if (e instanceof DuplicateBuildingException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} else if (e instanceof DuplicateDeskException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} else if (cause instanceof DuplicateTopicException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(cause.getMessage());
		}else{return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");}
	}
}
