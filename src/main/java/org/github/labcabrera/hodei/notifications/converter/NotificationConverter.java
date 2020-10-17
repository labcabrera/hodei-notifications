package org.github.labcabrera.hodei.notifications.converter;

import java.time.LocalDateTime;

import org.github.labcabrera.hodei.notifications.dto.NotificationRequest;
import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter implements Converter<NotificationRequest, NotificationEntity> {

	@Override
	public NotificationEntity convert(NotificationRequest source) {
		return NotificationEntity.builder()
			.module(source.getModule())
			.operation(source.getOperation())
			.subject(source.getSubject())
			.body(source.getBody())
			.timestamp(LocalDateTime.now())
			.build();
	}

}
