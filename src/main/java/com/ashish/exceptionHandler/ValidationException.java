package com.ashish.exceptionHandler;

import java.util.Map;

public class ValidationException extends RuntimeException {
	
	private Map<String, Object> error;

	public ValidationException(Map<String, Object> error) {
		super("validation failed");
		this.error = error;
	}
	public Map<String, Object> geterrors(){
		return error;
	}
	

}