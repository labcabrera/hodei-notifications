package org.github.labcabrera.hodei.notifications.service;

import java.util.Optional;

import org.github.labcabrera.hodei.notifications.dto.NotificationType;
import org.github.labcabrera.hodei.notifications.model.NotificationConfiguration;
import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConfigurationService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Cacheable("notificationsConfigurationsEnabled")
	public boolean isEnabled(String connector, NotificationEntity notification) {
		String module = notification.getModule();
		Optional<NotificationConfiguration> optional = readConfiguration(connector, module);
		if (!optional.isPresent()) {
			log.debug("Missing configuration for module {} using connector {}", module, connector);
			return false;
		}
		NotificationConfiguration configuration = optional.get();
		String operation = notification.getOperation();

		if (notification.getType() == NotificationType.ERROR && !configuration.getEnabledErrors()) {
			log.debug("Module {} is disabled for errors using connector {}", module, connector);
			return false;
		}
		if (notification.getType() == NotificationType.NOTIFICATION && !configuration.getEnabledNotifications()) {
			log.debug("Module {} is disabled for notifications using connector {}", module, connector);
			return false;
		}
		if (configuration.getExcluedOperations() != null && configuration.getExcluedOperations().contains(operation)) {
			log.debug("Excluded operation {} in module {} for connector {}", operation, module, connector);
			return false;
		}
		return true;
	}

	@Cacheable("notificationsConfigurations")
	public Optional<NotificationConfiguration> readConfiguration(String connector, String module) {
		Query query = new Query(Criteria.where("module").is(module).and("connector").is(connector));
		NotificationConfiguration entity = mongoTemplate.findOne(query, NotificationConfiguration.class);
		return Optional.ofNullable(entity);
	}
}
