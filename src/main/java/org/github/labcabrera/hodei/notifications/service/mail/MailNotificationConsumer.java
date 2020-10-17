package org.github.labcabrera.hodei.notifications.service.mail;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.github.labcabrera.hodei.notifications.model.NotificationConfiguration;
import org.github.labcabrera.hodei.notifications.model.NotificationEntity;
import org.github.labcabrera.hodei.notifications.service.NotificationConfigurationService;
import org.github.labcabrera.hodei.notifications.service.NotificationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnBean(JavaMailSender.class)
@Slf4j
public class MailNotificationConsumer implements NotificationConsumer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private NotificationConfigurationService notificationConfigurationService;

	@Value("${app.notifications.mail.from}")
	private String from;

	@Override
	public void accept(NotificationEntity notification) {
		String connector = getConnectorName();
		String module = notification.getModule();
		if (notificationConfigurationService.isEnabled(connector, notification)) {
			NotificationConfiguration configuration = notificationConfigurationService.readConfiguration(connector, module)
				.orElseThrow(() -> new RuntimeException("Missing configuration"));
			List<String> recipients = configuration.getRecipients();
			String subject = notification.getSubject();
			String body = notification.getBody();
			sendMessage(subject, body, recipients);
		}
	}

	@Override
	public String getConnectorName() {
		return "mail";
	}

	private void sendMessage(String subject, String body, List<String> recipients) {
		log.debug("Sending mail {} to {}", subject, recipients);
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(recipients.toArray(new String[recipients.size()]));
			helper.setSubject(subject);
			helper.setText(body);
			mailSender.send(msg);
		}
		catch (Exception ex) {
			log.error("Mail send error: {}", ex.getMessage(), ex);
		}
	}
}
