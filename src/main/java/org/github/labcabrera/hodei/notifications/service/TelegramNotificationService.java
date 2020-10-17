package org.github.labcabrera.hodei.notifications.service;

import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.github.labcabrera.hodei.notifications.service.telegram.TelegramClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationService {

	@Autowired
	private TelegramClient telegramClient;

	public void processNotification(NotificationEntity notification) {
		telegramClient.sendMessage(notification.getSubject());
	}

}
