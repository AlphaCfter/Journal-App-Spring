package com.alphacfter.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	/**
	 * Configures the transaction manager for MongoDB.
	 *
	 * This bean enables transactional support for MongoDB operations,
	 * allowing methods annotated with {@code @Transactional} to ensure
	 * atomicity and consistency during multi-document operations.
	 *
	 * @param dbfactory the MongoDB database factory used to create connections.
	 * @return a {@code MongoTransactionManager} instance for managing transactions.
	 */
	@Bean
	public PlatformTransactionManager db(MongoDatabaseFactory dbfactory) {
		return new MongoTransactionManager(dbfactory);
	}

	/**
	 * Solves: Field restTemplate in com.alphacfter.journalApp.service.WeatherAPI
	 * required a bean of type 'org.springframework.web.client.RestTemplate' that could not be found.
	 *
	 * All these methods written in @Bean are written purposefully to instantiate an
	 * instance of the RestTemplate. The method signatures have no significant values
	 * because the other way they would return an instance of RestTemplate
	 *
	 * @see com.alphacfter.journalApp.controller.UserEntryController
	 * @return an instance of RestTemplate which is required during component scanning
	 */
	@Bean
	public RestTemplate temp(){
		return new RestTemplate();
	}
}
