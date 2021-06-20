package com.kakao.assignment.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // Common
	RESPONSE_CODE_SUCCESS(1, "C001", "SUCCESS"),
	RESPONSE_CODE_FAIL(-100, "C002", "FAIL"),
	RESPONSE_CODE_NOT_DEFINED(-130, "C999", "Code is not defined."),

    // biz
	RESPONSE_CODE_SOLDOUT(101, "B001", "The select asset is sold out"),
    ;
    private int status;
    private final String code;
    private final String message;


    ResponseCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
