package com.mindtree.springbootWeb.controller;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mindtree.springbootWeb.utility.exception.DefaultExceptionAttributes;
import com.mindtree.springbootWeb.utility.exception.ExceptionAttributes;

/*
 *  A Parent controller for any controller hierarchy
 *  Here we have two methods to handle any exception thrown by the hierarchy controllers
 *  which builds a response body and sends it in json instead of throwing stack trace
 */

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * Controller method to handle any exception as 500 error
	 */

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest httpRequest){
		
		logger.error("> handlerException");
		logger.error("Exception :");
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, httpRequest, HttpStatus.INTERNAL_SERVER_ERROR);
		
		logger.error("< handlerException");

		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/*
	 * Controller method to handle resource not found / No result exception as 404 error
	 * 
	 */
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Map<String, Object>> handleNoResultException(NoResultException noResultException, HttpServletRequest httpRequest){
		
		logger.error("> handleNoResultException");
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, httpRequest, HttpStatus.NOT_FOUND);
		
		logger.error("< handleNoResultException");
		
		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.NOT_FOUND);
	}
	
}
