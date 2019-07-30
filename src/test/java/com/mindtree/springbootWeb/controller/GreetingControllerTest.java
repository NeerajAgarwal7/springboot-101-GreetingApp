package com.mindtree.springbootWeb.controller;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.springbootWeb.AbstractControllerTest;
import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.service.GreetingService;

import org.junit.Assert;

@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

	@Autowired
	private GreetingService service;

	@Before
	public void setUp() {
		// Run some logic before testing begins
		// At most one before method is supported
		super.setUp();
		service.evictCache();
	}

	@After
	public void cleanUp() {
		// Runs after the testing is completed
		service.evictCache();
	}

	@Test
	public void testGetGreetings() throws Exception {

		String uri = "/api/greetings";
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 200", 200, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		BigInteger id = BigInteger.ONE;
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 200", 200, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetGreetingNotFound() throws Exception {

		String uri = "/api/greetings/{id}";
		BigInteger id = BigInteger.valueOf(Long.MAX_VALUE);
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 404", 404, status);
		Assert.assertTrue("failure - Expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void testCreateGreeting() throws Exception {

		String uri = "/api/greetings";
		Greeting entity = new Greeting();
		entity.setText("Hello");
		// Convert object to json string
		String inputJson = super.mapToJson(entity);
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 201", 201, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

		// Convert json response to string
		Greeting createdGreeting = super.mapFromJson(content, Greeting.class);
		Assert.assertNotNull("Failure - expected not null", createdGreeting);
		Assert.assertNotNull("Failure - expected id attribute not null", createdGreeting.getId());
		Assert.assertEquals("Failure - expected text attribute to match", entity.getText(), createdGreeting.getText());

	}
	
	@Test
	public void testUpdateGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		BigInteger id = BigInteger.ONE;
		Greeting entity = service.findOne(id);
		String updatedText = "Hello";
		entity.setText(updatedText);
		
		// Convert object to json string
		String inputJson = super.mapToJson(entity);
		
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 200", 200, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

		// Convert json response to string
		Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);
		Assert.assertNotNull("Failure - expected not null", updatedGreeting);
		Assert.assertNotNull("Failure - expected id attribute not null", updatedGreeting.getId());
		Assert.assertEquals("Failure - expected text attribute to match", entity.getText(), updatedGreeting.getText());

	}
	
	@Test
	public void testDeleteGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		BigInteger id = BigInteger.ONE;

		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - Expected status code 204", 204, status);
		Assert.assertTrue("failure - Expected HTTP response body to be empty", content.trim().length() == 0);

		Greeting deletedGreeting = service.findOne(id);
		Assert.assertNull("Failure - expected greeting to be deleted", deletedGreeting);

	}

}
