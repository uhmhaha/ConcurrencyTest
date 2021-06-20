package com.kakao.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SoldoutException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public SoldoutException() {
	        super();
	    }
	    public SoldoutException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public SoldoutException(String message) {
	        super(message);
	    }
	    public SoldoutException(Throwable cause) {
	        super(cause);
	    }
}
