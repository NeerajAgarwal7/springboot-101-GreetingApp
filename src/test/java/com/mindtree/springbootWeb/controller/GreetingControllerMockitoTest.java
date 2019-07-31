package com.mindtree.springbootWeb.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.springbootWeb.AbstractControllerTest;
import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.service.EmailService;
import com.mindtree.springbootWeb.service.GreetingService;

/*
 *  Mockito does not hit the service layer
 *  Instead it mocks the result to be received when function call is made 
 */

@Transactional
public class GreetingControllerMockitoTest extends AbstractControllerTest {

	// @Mock produces test stub methods of the class

	@Mock
	private EmailService emailService;

	@Mock
	private GreetingService greetingService;

	// @InjectMocks injects mocked objects
	@InjectMocks
	private GreetingController greetingController;

	// Helper methods to produce test data
	private Collection<Greeting> getEntityListStubData() {
		Collection<Greeting> list = new ArrayList<Greeting>();
		list.add(getEntityStubData());
		return list;
	}

	private Greeting getEntityStubData() {
		Greeting entity = new Greeting();
		entity.setId(BigInteger.ONE);
		entity.setText("Hello");
		return entity;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setUp(greetingController);
	}

	@Test
	public void testGetGreetings() throws Exception {

		// Mock data
		Collection<Greeting> list = getEntityListStubData();

		// Mock the result
		when(greetingService.findAll()).thenReturn(list);

		// Perform the test
		String uri = "/api/greetings";
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify if the method was invoked
		verify(greetingService, times(1)).findAll();

		// Perform standard Junit assertions
		Assert.assertEquals("Failure - Expected status code 200", 200, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetGreeting() throws Exception {

		// Mock data
		BigInteger id = BigInteger.ONE;
		Greeting greeting = getEntityStubData();

		// Mock the result
		when(greetingService.findOne(id)).thenReturn(greeting);

		String uri = "/api/greetings/{id}";
		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify if the method was invoked
		verify(greetingService, times(1)).findOne(id);

		Assert.assertEquals("Failure - Expected status code 200", 200, status);
		Assert.assertTrue("failure - Expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetGreetingNotFound() throws Exception {

		// Mock data
		BigInteger id = BigInteger.valueOf(Long.MAX_VALUE);

		// Mock the result
		when(greetingService.findOne(id)).thenReturn(null);

		String uri = "/api/greetings/{id}";

		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify if the method was invoked
		verify(greetingService, times(1)).findOne(id);

		Assert.assertEquals("Failure - Expected status code 404", 404, status);
		Assert.assertTrue("failure - Expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void testCreateGreeting() throws Exception {

		// Mock data
		Greeting greeting = getEntityStubData();

		// Mock the result
		when(greetingService.create(any(Greeting.class))).thenReturn(greeting);

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

		// Verify if the method was invoked
		verify(greetingService, times(1)).create(any(Greeting.class));

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

		// Mock data
		BigInteger id = BigInteger.ONE;
		Greeting entity = getEntityStubData();

		String uri = "/api/greetings/{id}";
		String updatedText = "Hello";
		entity.setText(updatedText);

		// Mock the result
		when(greetingService.update(any(Greeting.class))).thenReturn(entity);

		// Convert object to json string
		String inputJson = super.mapToJson(entity);

		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify if the method was invoked
		verify(greetingService, times(1)).update(any(Greeting.class));

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

		// No need to mock the delete result

		// Mocks a request to controller
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify if the method was invoked
		verify(greetingService, times(1)).delete(id);

		Assert.assertEquals("Failure - Expected status code 204", 204, status);
		Assert.assertTrue("failure - Expected HTTP response body to be empty", content.trim().length() == 0);

	}

}
