package com.mindtree.springbootWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Cacheable
@EnableScheduling
@EnableAsync
public class SpringbootWebApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		/*
		 * Simple in memory cache Manager class. 
		 * Suitable for POC or demo.
		 * Not suitable for Production environment
		 */
		
		/*
		 * ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
		 */		
		
		
		/* 
		 * Guava cache manager is deprecated in springboot 2.0 
		 * GuavaCacheManager cacheManager = new GuavaCacheManager();
		 */
		
		/*
		 * Caffine is a replacement for Guava
		 * Suiatable for production as it more robust and reliable
		 */
		
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("greetings");
		return cacheManager;
	}
	
}
