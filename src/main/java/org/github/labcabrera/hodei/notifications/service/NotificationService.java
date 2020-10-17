package org.github.labcabrera.hodei.notifications.service;

import javax.validation.Valid;

import org.github.labcabrera.hodei.notifications.dto.NotificationRequest;
import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
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

	@Autowired
	private Converter<NotificationRequest, NotificationEntity> converter;

	public void processNotification(@Valid NotificationRequest notification) {
		log.debug("Received notification {}", notification);
		NotificationEntity entity = converter.convert(notification);
		mongoTemplate.save(entity);
		telegramService.processNotification(entity);
	}

}
