package com.mindtree.springbootWeb.service;

import java.util.concurrent.CompletableFuture;
import com.mindtree.springbootWeb.model.Greeting;

public interface EmailService {

	Boolean send(Greeting greeting);
	
	void sendAsync(Greeting greeting);
	
	CompletableFuture<Boolean> sendAsyncWithResult(Greeting greeting);
	
}
