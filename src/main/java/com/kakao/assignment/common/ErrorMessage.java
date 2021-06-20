package com.kakao.assignment.common;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ErrorMessage {

	String responseCode;
	String responseMessage;
	
	public ErrorMessage() {
		this.responseCode = ResponseCode.RESPONSE_CODE_NOT_DEFINED.getCode();
		this.responseMessage = ResponseCode.RESPONSE_CODE_NOT_DEFINED.getMessage();
	}
	
	public ErrorMessage(ResponseCode code) {
		this.responseCode = code.getCode();
		this.responseMessage = code.getMessage();
	}
}
