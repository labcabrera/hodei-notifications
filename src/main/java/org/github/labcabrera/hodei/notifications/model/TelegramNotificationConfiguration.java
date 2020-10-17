package org.github.labcabrera.hodei.notifications.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "telegramNotificationConfigurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegramNotificationConfiguration {

	@Id
	private String id;

	private Boolean enabled;

	private String module;

	private List<String> excluedOperations;

}
