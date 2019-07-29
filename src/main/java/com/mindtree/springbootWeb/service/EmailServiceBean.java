package com.mindtree.springbootWeb.service;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.mindtree.springbootWeb.model.Greeting;

/*
 * This class does not contain code to send an email
 * We use time delays to illustrate the time taken if an email was actually sent
 */

@Service
public class EmailServiceBean implements EmailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// Simulate Synchronous process
	@Override
	public Boolean send(Greeting greeting) {

		logger.info("> Send Sync");
		Boolean success = Boolean.FALSE;

		// Simulate Method execution time
		long pause = 5000;
		try {
			Thread.sleep(pause);
		} catch (Exception e) {
			// Do nothing
		}
		logger.info("Processing time was {} sec", pause / 1000);
		success = Boolean.TRUE;
		logger.info("< Send Sync");
		return success;
	}

	// Simulate Async process without any return result
	@Async
	@Override
	public void sendAsync(Greeting greeting) {
		logger.info("> Send Async");

		try {
			send(greeting);
		} catch (Exception e) {
			logger.warn("Probelm sending async email", e);
		}

		logger.info("< Send Async");
	}

	// Simulate Async process with return result
	// Also known as fire and forget process
	@Async
	@Override
	public CompletableFuture<Boolean> sendAsyncWithResult(Greeting greeting) {
		logger.info("> Send Async with result");

		CompletableFuture<Boolean> respose = new CompletableFuture<Boolean>();
		try {
			Boolean success = send(greeting);
			respose.complete(success);
		} catch (Exception e) {
			logger.warn("Probelm sending async email", e);
			respose.completeExceptionally(e);
		}
		
		logger.info("< Send Async with result");
		return respose;
	}

}
