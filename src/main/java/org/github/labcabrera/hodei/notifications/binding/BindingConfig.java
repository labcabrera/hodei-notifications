package org.github.labcabrera.hodei.notifications.binding;

import java.util.function.Consumer;

import org.github.labcabrera.hodei.notifications.dto.NotificationRequest;
import org.github.labcabrera.hodei.notifications.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {

	@Bean
	public Consumer<NotificationRequest> processNotification(NotificationService service) {
		return service::processNotification;
	}

}
