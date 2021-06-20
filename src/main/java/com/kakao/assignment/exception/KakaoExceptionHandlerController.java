package com.kakao.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kakao.assignment.common.ErrorMessage;
import com.kakao.assignment.common.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class KakaoExceptionHandlerController {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ErrorMessage> defalut(CommonException e) {
		log.error("CommonException", e);

		ErrorMessage response = new ErrorMessage(ResponseCode.RESPONSE_CODE_FAIL);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(SoldoutException.class)
	public ResponseEntity<ErrorMessage> soldOutExceptionHandler(SoldoutException e) {
		log.error("soldOutExceptionHandler", e);

		ErrorMessage response = new ErrorMessage(ResponseCode.RESPONSE_CODE_SOLDOUT);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
