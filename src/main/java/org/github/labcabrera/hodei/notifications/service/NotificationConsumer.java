package org.github.labcabrera.hodei.notifications.service;

import java.util.function.Consumer;

import org.github.labcabrera.hodei.notifications.model.NotificationEntity;

public interface NotificationConsumer extends Consumer<NotificationEntity> {

	String getConnectorName();

}
