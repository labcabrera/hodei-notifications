package org.github.labcabrera.hodei.notifications.service;

import java.io.InputStream;
import java.util.List;

import org.github.labcabrera.hodei.notifications.model.NotificationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConfigurationPopulator {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public void checkConfiguration() {
		if (mongoTemplate.count(new Query(), NotificationConfiguration.class) > 0L) {
			return;
		}
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/notification-configuration.json");
			List<NotificationConfiguration> list = objectMapper.readerFor(new TypeReference<List<NotificationConfiguration>>() {
			}).readValue(in);
			list.stream().forEach(e -> mongoTemplate.save(e));
		}
		catch (Exception ex) {
			log.error("Error loading initial configuration");
		}
	}

}
