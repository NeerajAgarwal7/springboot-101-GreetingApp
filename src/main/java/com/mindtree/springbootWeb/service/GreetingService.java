package com.mindtree.springbootWeb.service;

import java.math.BigInteger;
import java.util.Collection;

import com.mindtree.springbootWeb.model.Greeting;

public interface GreetingService {
	
	Collection<Greeting> findAll();
	
	Greeting findOne(BigInteger id);
	
	Greeting create(Greeting greeting);
	
	Greeting update(Greeting greeting);
	
	void delete(BigInteger id);
	
	void evictCache();

}
