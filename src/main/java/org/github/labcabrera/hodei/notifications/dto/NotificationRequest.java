package org.github.labcabrera.hodei.notifications.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.github.labcabrera.hodei.notifications.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

	@NotNull(message = "Required type")
	private NotificationType type;

	@NotEmpty(message = "Required module")
	private String module;

	@NotEmpty(message = "Required operation")
	private String operation;

	@NotEmpty(message = "Required subject")
	private String subject;

	private String body;

}
