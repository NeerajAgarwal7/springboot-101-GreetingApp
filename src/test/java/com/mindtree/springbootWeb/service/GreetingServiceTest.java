package com.mindtree.springbootWeb.service;

import java.math.BigInteger;
import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.springbootWeb.AbstractTest;
import com.mindtree.springbootWeb.model.Greeting;

@Transactional
public class GreetingServiceTest extends AbstractTest {

	@Autowired
	private GreetingService service;

	@Before
	public void setUp() {
		// Run some logic before testing begins
		// At most one before method is supported
		service.evictCache();
	}

	@After
	public void cleanUp() {
		// Runs after the testing is completed
		service.evictCache();
	}

	@Test
	public void testFindAll() {

		Collection<Greeting> list = service.findAll();
		Assert.assertNotNull("Failure - Expected not null", list);
		Assert.assertEquals("Failure - expected size", 2, list.size());

	}

	@Test
	public void testFindOne() {

		BigInteger id = BigInteger.ONE;
		Greeting entity = service.findOne(id);

		Assert.assertNotNull("Failure - Expected not null", entity);
		Assert.assertEquals("Failure - expected id attribute match", BigInteger.ONE, entity.getId());
	}

	@Test
	public void testFindOneNotFound() {

		BigInteger id = BigInteger.valueOf(Long.MAX_VALUE);
		Greeting entity = service.findOne(id);
		Assert.assertNull("Failure - expected null", entity);
	}

	@Test
	public void testCreate() {

		Greeting entity = new Greeting();
		entity.setText("Hello");
		Greeting createdGreeting = service.create(entity);

		Assert.assertNotNull("Failure - expected not null", createdGreeting);
		Assert.assertNotNull("Failure - expected id attribute not null", createdGreeting.getId());
		Assert.assertEquals("Failure - expected text attribute to match", entity.getText(),
				createdGreeting.getText());

		Collection<Greeting> list = service.findAll();
		Assert.assertEquals("Failure - expected size", 3, list.size());

	}

	@Test
	public void testCreateWithId() {

		Exception e = null;
		Greeting entity = new Greeting();
		entity.setId(BigInteger.valueOf(Long.MAX_VALUE));
		entity.setText("Hello");

		try {
			service.create(entity);
		} catch (EntityExistsException ex) {
			e = ex;
		}

		Assert.assertNotNull("Failure - expected exception", e);
		Assert.assertTrue("Failure - expected EntityExistsException", e instanceof EntityExistsException);
	}

	@Test
	public void testUpadte() {

		BigInteger id = BigInteger.ONE;
		Greeting entity = service.findOne(id);

		Assert.assertNotNull("Failure - expected not null", entity);

		String updatedText = "Hello";
		entity.setText(updatedText);
		Greeting updatedGreeting = service.update(entity);

		Assert.assertNotNull("Failure - expected updated greeting not null", updatedGreeting);
		Assert.assertEquals("Failure - expected updated greeting id unchanged", BigInteger.ONE, updatedGreeting.getId());
		Assert.assertEquals("Failure - expected updated greeting text attribute match", updatedText,
				updatedGreeting.getText());

	}

	@Test
	public void testUpdateNotFound() {

		Exception e = null;
		Greeting entity = new Greeting();
		BigInteger id = BigInteger.valueOf(Long.MAX_VALUE);
		entity.setId(id);
		entity.setText("Hello");

		try {
			service.update(entity);
		} catch (NoResultException ex) {
			e = ex;
		}

		Assert.assertNotNull("Failure - expected exception", e);
		Assert.assertTrue("Failure - Expected NoResultException", e instanceof NoResultException);
	}

	@Test
	public void testDelete() {

		BigInteger id = BigInteger.ONE;
		Greeting entity = service.findOne(id);

		Assert.assertNotNull("Failure - expected not null", entity);

		service.delete(id);

		Collection<Greeting> list = service.findAll();

		Assert.assertEquals("Failure - expected size", 1, list.size());
		Greeting deletedGreeting = service.findOne(id);
		Assert.assertNull("Failure - expected greeting to be deleted", deletedGreeting);

	}

}
