package com.mindtree.springbootWeb.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.repository.GreetingRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;

	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(value = "greetings", key = "#id")
	public Greeting findOne(BigInteger id) {
		try {
			Greeting greeting = greetingRepository.findById(id).get();
			return greeting;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "result.id")
	public Greeting create(Greeting greeting) {
		if (greeting.getId() != null) {
			// cannot create greeting with existing id
			throw new EntityExistsException();
		}

		Greeting savedGreeting = greetingRepository.save(greeting);

		/*
		 * Illustrate a transaction exception. If id is 4 then throw runtime exception.
		 * Any runtime exception thrown from a transaction method results in a roll back
		 * from db commit.
		 */

		if (savedGreeting.getId() == BigInteger.valueOf(4)) {
			throw new RuntimeException("ID 4 found, Roll back!");
		}
		return savedGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "greeting.id")
	public Greeting update(Greeting greeting) {
		Greeting persistedGreeting = findOne(greeting.getId());
		if (persistedGreeting == null) {
			// cannot update non existing entity
			throw new NoResultException();
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "greetings", key = "#id")
	public void delete(BigInteger id) {
		greetingRepository.deleteById(id);
	}

	// Clear cache
	@Override
	@CacheEvict(value = "greetings", allEntries = true)
	public void evictCache() {
	}

}
