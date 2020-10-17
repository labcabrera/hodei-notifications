package org.github.labcabrera.hodei.notifications.service;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.github.labcabrera.hodei.notifications.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;

@Service
@Validated
@Slf4j
public class NotificationService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TelegramNotificationService telegramService;

	public void processNotification(NotificationRequest notification) {
		log.debug("Received notification {}", notification);
		try {
			process(notification);
		}
		catch (ConstraintViolationException ex) {
			log.error("Received invalid notification: {}", ex.getMessage());
		}
	}

	private void process(@Valid NotificationRequest notification) {
		mongoTemplate.save(notification);
		telegramService.processNotification(notification);

	}

}
