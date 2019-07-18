package com.mindtree.springbootWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootWebApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(SpringbootWebApplication.class, args);
	}

}
