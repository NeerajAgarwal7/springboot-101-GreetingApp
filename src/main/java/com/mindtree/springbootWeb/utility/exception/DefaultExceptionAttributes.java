package com.mindtree.springbootWeb.utility.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

/*
 * This is util class to handle servlet exception
 * It builds an exception response to be sent by the exception handler controller
 */

public class DefaultExceptionAttributes implements ExceptionAttributes {
	
	public static final String TIMESTAMP = "timestamp";
	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String EXCEPTION = "exception";
	public static final String MESSAGE = "message";
	public static final String PATH = "path";
	
	@Override
	public Map<String, Object> getExceptionAttributes(Exception  exception, HttpServletRequest httpRequest,
			HttpStatus httpStatus) {

		Map<String, Object> exceptionAttributes = new LinkedHashMap<String, Object>();
		
		exceptionAttributes.put(TIMESTAMP, new Date());
		addHttpStatus(exceptionAttributes, httpStatus);
		addExceptionDetail(exceptionAttributes, exception);
		addPath(exceptionAttributes, httpRequest);
		
		return exceptionAttributes;
	}

	private void addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus) {
		exceptionAttributes.put(STATUS, httpStatus.value());
		exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
	}
	
	private void addExceptionDetail(Map<String, Object> exceptionAttributes,Exception exception) {
		exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
		exceptionAttributes.put(MESSAGE, exception.getMessage());
	}
	
	private void addPath(Map<String, Object> exceptionAttributes,HttpServletRequest httpRequest) {
		exceptionAttributes.put(PATH, httpRequest.getServletPath());
	}
	
}
