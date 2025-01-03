package com.alphacfter.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
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

}
