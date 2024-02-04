package com.hms.www.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice 
public class CommonExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public String exception(Exception ex) {
		// 주로 500 Exception 발생
		return "error_page";
	}
	
	// 404 Exception은 특별히 따로 관리
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 실질적인 404 Exception 상태
	public String handler404(NoHandlerFoundException ex) {
		return "custom404";
	}
}
