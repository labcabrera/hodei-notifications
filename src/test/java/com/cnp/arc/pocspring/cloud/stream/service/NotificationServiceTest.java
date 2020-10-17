package com.cnp.arc.pocspring.cloud.stream.service;

import javax.validation.ConstraintViolationException;

import org.github.labcabrera.hodei.notifications.HodeiNotificationsApplication;
import org.github.labcabrera.hodei.notifications.dto.NotificationRequest;
import org.github.labcabrera.hodei.notifications.dto.NotificationType;
import org.github.labcabrera.hodei.notifications.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = HodeiNotificationsApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;

	@Test(expected = ConstraintViolationException.class)
	public void validationError() {
		NotificationRequest notification = NotificationRequest.builder().build();
		notificationService.processNotification(notification);
	}

	@Test
	public void testSuccess() {
		NotificationRequest notification = NotificationRequest.builder()
			.type(NotificationType.NOTIFICATION)
			.module("customers")
			.operation("customer-modification")
			.subject("Customer 123 has been modified")
			.body("Lorem ipsum")
			.build();
		notificationService.processNotification(notification);
	}
}
