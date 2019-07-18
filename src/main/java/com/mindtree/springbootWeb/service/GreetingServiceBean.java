package com.mindtree.springbootWeb.service;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.repository.GreetingRepository;

@Service
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;

	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	public Greeting findOne(BigInteger id) {	
		Greeting greeting = greetingRepository.getOne(id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) {
		if (greeting.getId() != null) {
			// cannot create greeting with existing id
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) {
		Greeting persistedGreeting = findOne(greeting.getId());
		if (persistedGreeting == null) {
			// cannot update non existing entity
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	public void delete(BigInteger id) {
		greetingRepository.deleteById(id);
	}

}
