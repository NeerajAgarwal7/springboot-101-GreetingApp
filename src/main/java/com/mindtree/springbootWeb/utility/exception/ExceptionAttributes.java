package com.mindtree.springbootWeb.utility.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public interface ExceptionAttributes {

	Map<String,Object> getExceptionAttributes(Exception exception, HttpServletRequest httRequest, HttpStatus httpStatus);
	
}
