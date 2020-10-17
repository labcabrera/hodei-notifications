package org.github.labcabrera.hodei.notifications;

import java.util.Arrays;

import org.github.labcabrera.hodei.notifications.model.NotificationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
public class HodeiNotificationsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HodeiNotificationsApplication.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void run(String... args) throws Exception {
		if (mongoTemplate.count(new Query(), NotificationConfiguration.class) == 0L) {
			log.debug("Loading sample notification configuration");
			mongoTemplate.save(NotificationConfiguration.builder()
				.connector("telegram")
				.module("customers")
				.enabled(true)
				.excluedOperations(Arrays.asList("entity-synchronization"))
				.build());
		}
	}

}
