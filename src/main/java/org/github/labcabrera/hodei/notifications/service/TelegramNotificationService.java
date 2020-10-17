package org.github.labcabrera.hodei.notifications.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.github.labcabrera.hodei.notifications.model.TelegramNotificationConfiguration;
import org.github.labcabrera.hodei.notifications.service.telegram.TelegramClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TelegramNotificationService {

	@Autowired
	private TelegramClient telegramClient;

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void populate() {
		if (mongoTemplate.count(new Query(), TelegramNotificationConfiguration.class) == 0L) {
			mongoTemplate.save(TelegramNotificationConfiguration.builder()
				.module("customers")
				.enabled(true)
				.excluedOperations(Arrays.asList("entity-synchronization"))
				.build());
		}
	}

	public void processNotification(NotificationEntity notification) {
		String module = notification.getModule();
		String operation = notification.getOperation();

		Query query = new Query(Criteria.where("module").is(module));
		TelegramNotificationConfiguration configuration = mongoTemplate.findOne(query, TelegramNotificationConfiguration.class);

		if (configuration == null) {
			log.debug("Missing telegram configuration for module {}", module);
		}
		else if (!Boolean.TRUE.equals(configuration.getEnabled())) {
			log.debug("Module {} is disabled", module);
		}
		else if (configuration.getExcluedOperations() != null && configuration.getExcluedOperations().contains(operation)) {
			log.debug("Excluded operation {} in module {}", operation, module);
		}
		else {
			telegramClient.accept(notification.getSubject());
		}
	}

}
