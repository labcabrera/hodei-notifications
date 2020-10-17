package org.github.labcabrera.hodei.notifications.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {

	NOTIFICATION("notification"),

	ERROR("error");

	private String value;

	private NotificationType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
