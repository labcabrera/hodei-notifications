package org.github.labcabrera.hodei.notifications.model;

import java.time.LocalDateTime;

import org.github.labcabrera.hodei.notifications.dto.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {

	@Id
	private String id;

	private NotificationType type;

	private String module;

	private LocalDateTime timestamp;

	private String operation;

	private String subject;

	private String body;

}
