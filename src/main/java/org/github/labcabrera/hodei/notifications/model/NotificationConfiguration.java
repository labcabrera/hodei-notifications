package org.github.labcabrera.hodei.notifications.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "notificationConfigurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationConfiguration {

	@Id
	private String id;

	private String connector;

	private Boolean enabled;

	private String module;

	private List<String> excluedOperations;

}