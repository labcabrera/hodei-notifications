package org.github.labcabrera.hodei.notifications.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

	@Id
	private String id;

	@NotEmpty
	private NotificationType type;

	@NotEmpty
	private String module;

	private LocalDateTime timestamp;

	@NotEmpty
	private String operation;

	@NotEmpty
	private String subject;

	private String body;

}
