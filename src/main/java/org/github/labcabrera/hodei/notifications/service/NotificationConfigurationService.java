package org.github.labcabrera.hodei.notifications.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.github.labcabrera.hodei.notifications.model.NotificationConfiguration;
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

	@PostConstruct
	public void populate() {
		// Configuration example
		if (mongoTemplate.count(new Query(), NotificationConfiguration.class) == 0L) {
			mongoTemplate.save(NotificationConfiguration.builder()
				.connector("telegram")
				.module("customers")
				.enabled(true)
				.excluedOperations(Arrays.asList("entity-synchronization"))
				.build());
		}
	}

	@Cacheable
	public boolean isEnabled(String connector, String module, String operation) {
		Query query = new Query(Criteria.where("module").is(module).and("connector").is(connector));
		NotificationConfiguration configuration = mongoTemplate.findOne(query, NotificationConfiguration.class);

		if (configuration == null) {
			log.debug("Missing configuration for module {} using connector {}", module, connector);
			return false;
		}

		if (!Boolean.TRUE.equals(configuration.getEnabled())) {
			log.debug("Module {} is disabled for connector {}", module, connector);
			return false;
		}

		if (configuration.getExcluedOperations() != null && configuration.getExcluedOperations().contains(operation)) {
			log.debug("Excluded operation {} in module {} for connector {}", operation, module, connector);
			return false;
		}

		return true;
	}

}
