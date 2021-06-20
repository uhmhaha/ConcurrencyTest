package com.kakao.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CommonException() {
	        super();
	    }
	    public CommonException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public CommonException(String message) {
	        super(message);
	    }
	    public CommonException(Throwable cause) {
	        super(cause);
	    }
}
