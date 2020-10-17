package org.github.labcabrera.hodei.notifications;

import org.github.labcabrera.hodei.notifications.service.ConfigurationPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HodeiNotificationsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HodeiNotificationsApplication.class, args);
	}

	@Autowired
	private ConfigurationPopulator configurationPopulator;

	@Override
	public void run(String... args) throws Exception {
		configurationPopulator.checkConfiguration();
	}

}
