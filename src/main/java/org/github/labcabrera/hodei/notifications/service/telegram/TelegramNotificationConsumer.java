package org.github.labcabrera.hodei.notifications.service.telegram;

import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.github.labcabrera.hodei.notifications.service.NotificationConfigurationService;
import org.github.labcabrera.hodei.notifications.service.NotificationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationConsumer implements NotificationConsumer {

	@Autowired
	private TelegramClient telegramClient;

	@Autowired
	private NotificationConfigurationService notificationConfigurationService;

	@Override
	public void accept(NotificationEntity notification) {
		String module = notification.getModule();
		String operation = notification.getOperation();
		String connector = getConnectorName();
		boolean enabled = notificationConfigurationService.isEnabled(connector, module, operation);
		if (enabled) {
			telegramClient.accept(notification.getSubject());
		}
	}

	@Override
	public String getConnectorName() {
		return "telegram";
	}

}
