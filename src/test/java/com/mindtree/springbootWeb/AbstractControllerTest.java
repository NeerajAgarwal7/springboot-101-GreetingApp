package com.mindtree.springbootWeb;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.springbootWeb.controller.GreetingController;

@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest {

	protected MockMvc mvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	// For junit test cases
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// For mockito test cases
	protected void setUp(GreetingController controller) {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	// Utility method to convert java objects to JSON
	protected String mapToJson(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);

	}

	// Utility method to parse JSON response
	protected <T>T  mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
}
