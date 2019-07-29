package com.mindtree.springbootWeb.controller;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.service.EmailService;
import com.mindtree.springbootWeb.service.GreetingService;

@RestController
public class GreetingController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {

		logger.info("> Find all");
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("< Find all");
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {

		logger.info("> Find one");
		Greeting greeting = greetingService.findOne(id);
		if (greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		logger.info("< Find one");
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {

		logger.info("> Create");
		Greeting savedGreeting = greetingService.create(greeting);
		logger.info("< Create");
		return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {

		logger.info("> Update");
		Greeting updatedGreeting = greetingService.update(greeting);
		if (updatedGreeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< Update");
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id, @RequestBody Greeting greeting) {

		logger.info("> Delete");
		greetingService.delete(id);
		logger.info("< Delete");
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/api/greetings/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") BigInteger id,
			@RequestParam(value = "wait", defaultValue = "false") boolean waitForResponse) {

		logger.info("> Send Greeting");
		Greeting greeting = null;

		try {
			greeting = greetingService.findOne(id);
			if (greeting == null) {
				logger.info("< Send Greeting");
				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
			}

			if (waitForResponse) {
				CompletableFuture<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
				boolean emailSent = asyncResponse.get();
				logger.info(" - Greeting email sent? {}", emailSent);
			} else {
				emailService.sendAsync(greeting);
			}
		} catch (Exception e) {
			logger.error("A problem occured while sending email", e);
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("< Send Greeting");
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);

	}

}