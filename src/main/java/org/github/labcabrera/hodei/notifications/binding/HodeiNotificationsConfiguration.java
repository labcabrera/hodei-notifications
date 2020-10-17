package org.github.labcabrera.hodei.notifications.binding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HodeiNotificationsConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
